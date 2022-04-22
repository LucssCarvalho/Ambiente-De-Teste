package com.example.chatKotlin.chat.Activity

import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.chatKotlin.R
import com.example.chatKotlin.chat.Adapter.MessageAdapter
import com.example.chatKotlin.chat.FirebaseConfig.FirebaseConfig
import com.example.chatKotlin.chat.FirebaseConfig.FirebaseUtils.FIREBASE_TAG_CHATS
import com.example.chatKotlin.chat.FirebaseConfig.FirebaseUtils.FIREBASE_TAG_MESSAGES
import com.example.chatKotlin.chat.Fragments.ContactsFragment.Companion.CONTACT_EMAIL
import com.example.chatKotlin.chat.Fragments.ContactsFragment.Companion.CONTACT_NAME
import com.example.chatKotlin.chat.Model.Chat
import com.example.chatKotlin.chat.Model.Message
import com.example.chatKotlin.chat.helper.Base64Custom
import com.example.chatKotlin.chat.helper.Preferences
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChatActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var nameRecipientUser: String
    private lateinit var emailRecipientUserId: String
    private lateinit var idRecipientUser: String
    private lateinit var idSenderUser: String
    private lateinit var nameSenderUser: String
    private lateinit var editMessage: EditText
    private lateinit var btnSend: ImageButton
    private lateinit var databaseReference: DatabaseReference
    private lateinit var listView: ListView
    private lateinit var arrayList: ArrayList<Message>
    private lateinit var arrayAdapter: ArrayAdapter<Message>
    private lateinit var valueEventListener: ValueEventListener


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        editMessage = findViewById(R.id.edit_message)
        btnSend = findViewById(R.id.btn_send)
        listView = findViewById(R.id.lv_chat)

        val extra: Bundle = intent.extras!!
        nameRecipientUser = extra.getString(CONTACT_NAME, "")
        emailRecipientUserId = extra.getString(CONTACT_EMAIL, "")
        idRecipientUser = Base64Custom().encodeBase64(emailRecipientUserId)

        val preferences = Preferences(this)
        idSenderUser = preferences.getIdentification().toString()
        nameSenderUser = preferences.getName().toString()

        toolbar = findViewById(R.id.tb_chat)
        toolbar.title = nameRecipientUser
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        setSupportActionBar(toolbar)

        arrayList = ArrayList()
        arrayAdapter = MessageAdapter(this, arrayList)
        listView.adapter = arrayAdapter

        databaseReference =
            FirebaseConfig.getDatabaseReference().child(FIREBASE_TAG_MESSAGES).child(idSenderUser)
                .child(idRecipientUser)

        valueEventListener = (object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                arrayList.clear()

                for (data: DataSnapshot in snapshot.children) {
                    val message: Message = data.getValue(Message::class.java) as Message
                    arrayList.add(message)
                }
                arrayAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        databaseReference.addValueEventListener(valueEventListener)

        btnSend.setOnClickListener {
            val textMessage = editMessage.text.toString()

            if (textMessage.isEmpty()) {
                Toast.makeText(this, "Enter a message to send", Toast.LENGTH_SHORT).show()
            } else {

                val sdf = SimpleDateFormat("hh:mm")
                val currentDate = sdf.format(Date())

                val message: Message = Message().apply {
                    userId = idSenderUser
                    message = textMessage
                    hourMessage = currentDate
                }

                val returnMessageSender = sendMessage(idSenderUser, idRecipientUser, message)
                if (!returnMessageSender) {
                    Toast.makeText(this, "Error save message", Toast.LENGTH_LONG)
                } else {
                    val returnMessageRecipient = sendMessage(idRecipientUser, idSenderUser, message)
                    if (!returnMessageRecipient) {
                        Toast.makeText(this, "Error send message", Toast.LENGTH_LONG)
                    }
                }

                val chat: Chat = Chat().apply {
                    userId = idRecipientUser
                    name = nameRecipientUser
                    lastMessage = textMessage
                }

                val returnMessageRecipient = saveChat(idSenderUser, idRecipientUser, chat)
                if (!returnMessageRecipient) {
                    Toast.makeText(this, "Error save chat", Toast.LENGTH_LONG)
                } else {
                    val chat: Chat = Chat().apply {
                        userId = idSenderUser
                        name = nameSenderUser
                        lastMessage = textMessage
                    }
                    val returnMessageSender = saveChat(idRecipientUser, idSenderUser, chat)
                    if (!returnMessageSender) {
                        Toast.makeText(this, "Error saving chat to destination", Toast.LENGTH_LONG)
                    }
                }

                editMessage.setText("")
            }
        }
    }

    private fun saveChat(
        idSenderUser: String,
        idRecipientUser: String,
        chat: Chat
    ): Boolean {
        return try {
            databaseReference = FirebaseConfig.getDatabaseReference().child(FIREBASE_TAG_CHATS)
            databaseReference.child(idSenderUser).child(idRecipientUser).setValue(chat)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun sendMessage(
        idSenderUser: String,
        idRecipientUser: String,
        textMessage: Message
    ): Boolean {
        return try {
            databaseReference = FirebaseConfig.getDatabaseReference().child(FIREBASE_TAG_MESSAGES)
            databaseReference.child(idSenderUser).child(idRecipientUser).push()
                .setValue(textMessage)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override fun onStop() {
        super.onStop()
        databaseReference.removeEventListener(valueEventListener)
    }
}
