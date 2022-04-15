package com.example.chatKotlin.chat.activity

import android.os.Build
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.chatKotlin.R
import com.example.chatKotlin.chat.FirebaseConfig.FirebaseConfig
import com.example.chatKotlin.chat.Fragments.ContactsFragment.Companion.CONTACT_EMAIL
import com.example.chatKotlin.chat.Fragments.ContactsFragment.Companion.CONTACT_NAME
import com.example.chatKotlin.chat.Model.Message
import com.example.chatKotlin.chat.helper.Base64Custom
import com.example.chatKotlin.chat.helper.Preferences
import com.google.firebase.database.DatabaseReference

class ChatActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var nameRecipientUser: String
    private lateinit var emailRecipientUserId: String
    private lateinit var idRecipientUser: String
    private lateinit var idSenderUser: String
    private lateinit var editMessage: EditText
    private lateinit var btnSend: ImageButton
    private lateinit var databaseReference: DatabaseReference


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        editMessage = findViewById(R.id.edit_message)
        btnSend = findViewById(R.id.btn_send)

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

        btnSend.setOnClickListener {
            val textMessage = editMessage.text.toString()

            if (textMessage.isEmpty()) {
                Toast.makeText(this, "Digite uma mensagem para enviar", Toast.LENGTH_SHORT).show()
            } else {
                val message: Message = Message().apply {
                    userId = idSenderUser
                    message = textMessage
                }
                saveMessage(idSenderUser, idRecipientUser, message)
            }
        }
    }

    private fun saveMessage(
        idSenderUser: String,
        idRecipientUser: String,
        textMessage: Message
    ): Boolean {
        return try {
            databaseReference = FirebaseConfig.getDatabaseReference().child("message")
            databaseReference.child(idSenderUser).child(idRecipientUser).push()
                .setValue(textMessage)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
