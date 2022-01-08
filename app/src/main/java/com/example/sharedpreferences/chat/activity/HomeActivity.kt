package com.example.sharedpreferences.chat.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.sharedpreferences.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var users: DatabaseReference
    private lateinit var userSearch: DatabaseReference
    private lateinit var textViewWelcomeUser: TextView
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        textViewWelcomeUser = findViewById(R.id.textWelcomeUser)
        textViewWelcomeUser.text = "Olá, "

    }

    fun signOut(view: View) {
        finish()
    }
}