package com.example.chatKotlin.chat.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.chatKotlin.R

class ChatActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        toolbar = findViewById(R.id.tb_chat)
        toolbar.title = "User"
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        setSupportActionBar(toolbar)

    }
}