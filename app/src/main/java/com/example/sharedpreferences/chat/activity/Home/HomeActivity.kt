package com.example.sharedpreferences.chat.activity.Home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity;
import com.example.sharedpreferences.R
import com.example.sharedpreferences.chat.FirebaseConfig.FirebaseConfig
import com.example.sharedpreferences.chat.activity.Login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        auth = FirebaseConfig.getFirebaseAuthentication()

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = "wpp"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            Toast.makeText(this, "Settings", Toast.LENGTH_LONG).show()
            true
        }
        R.id.item_logout -> {
            signOut()
            Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun signOut() {
        auth.signOut()
        returnLoginActivity()
    }

    private fun returnLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}