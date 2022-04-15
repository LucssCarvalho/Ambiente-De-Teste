package com.example.chatKotlin.chat.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.chatKotlin.R
import com.example.chatKotlin.chat.Fragments.ContactsFragment.Companion.CONTACT_NAME

class ChatActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var nameCurrentUser: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val extra: Bundle = intent.extras!!
        nameCurrentUser = extra.getString(CONTACT_NAME, "")

        toolbar = findViewById(R.id.tb_chat)
        toolbar.title = nameCurrentUser
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        setSupportActionBar(toolbar)


    }
}
