package com.example.chatKotlin.chat.Activity

import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.chatKotlin.R
import com.example.chatKotlin.chat.FirebaseConfig.FirebaseConfig
import com.example.chatKotlin.chat.Fragments.ContactsFragment.Companion.CONTACT_EMAIL
import com.example.chatKotlin.chat.Fragments.ContactsFragment.Companion.CONTACT_NAME
import com.example.chatKotlin.chat.Model.Contact
import com.example.chatKotlin.chat.Model.Message
import com.example.chatKotlin.chat.helper.Base64Custom
import com.example.chatKotlin.chat.helper.Preferences
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class ChatActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var nameRecipientUser: String
    private lateinit var emailRecipientUserId: String
    private lateinit var idRecipientUser: String
    private lateinit var idSenderUser: String
    private lateinit var editMessage: EditText
    private lateinit var btnSend: ImageButton
    private lateinit var databaseReference: DatabaseReference
    private lateinit var listView: ListView
    private lateinit var arrayList: ArrayList<String>
    private lateinit var arrayAdapter: ArrayAdapter<*>
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

        toolbar = findViewById(R.id.tb_chat)
        toolbar.title = nameRecipientUser
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        setSupportActionBar(toolbar)

        arrayList = ArrayList()
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList)
        listView.adapter = arrayAdapter

        databaseReference =
            FirebaseConfig.getDatabaseReference().child("messages").child(idSenderUser)
                .child(idRecipientUser)

        valueEventListener = (object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                arrayList.clear()

                for (data: DataSnapshot in snapshot.children) {
                    val message: Message = data.getValue(Message::class.java) as Message
                    arrayList.add(message.message)
                }
                arrayAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        databaseReference.addValueEventListener(valueEventListener)

        btnSend.setOnClickListener {
            val textMessage = editMessage.text.toString()

            if (textMessage.isEmpty()) {
                Toast.makeText(this, "Digite uma mensagem para enviar", Toast.LENGTH_SHORT).show()
            } else {
                val message: Message = Message().apply {
                    userId = idSenderUser
                    message = textMessage
                }
                sendMessage(idSenderUser, idRecipientUser, message)
                editMessage.setText("")
            }
        }
    }

    private fun sendMessage(
        idSenderUser: String,
        idRecipientUser: String,
        textMessage: Message
    ): Boolean {
        return try {
            databaseReference = FirebaseConfig.getDatabaseReference().child("messages")
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
