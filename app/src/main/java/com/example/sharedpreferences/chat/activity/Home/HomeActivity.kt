package com.example.sharedpreferences.chat.activity.Home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.sharedpreferences.R
import com.example.sharedpreferences.chat.FirebaseConfig.FirebaseConfig
import com.example.sharedpreferences.chat.activity.Login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private lateinit var textViewWelcomeUser: TextView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        textViewWelcomeUser = findViewById(R.id.textWelcomeUser)
        auth = FirebaseConfig.getFirebaseAuthentication()
    }

    fun signOut(view: View) {
        auth.signOut()
        returnLoginActivity()
    }

    private fun returnLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}