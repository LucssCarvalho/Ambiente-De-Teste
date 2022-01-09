package com.example.sharedpreferences.chat.activity.Home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.sharedpreferences.R
import com.example.sharedpreferences.chat.FirebaseConfig.FirebaseConfig
import com.example.sharedpreferences.chat.FirebaseConfig.FirebaseConfig.Companion.getDatabaseReference
import com.example.sharedpreferences.chat.domain.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.getValue

class HomeActivity : AppCompatActivity() {

    private lateinit var textViewWelcomeUser: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        textViewWelcomeUser = findViewById(R.id.textWelcomeUser)
        auth = FirebaseConfig.getFirebaseAuthentication()
        database = getDatabaseReference()
        database.child("users").child(auth.currentUser?.uid.toString()).get()
            .addOnSuccessListener {
                Log.i("firebase", "Got value ${it.value}")
                val user = returnUser(it)
                if (user != null) {
                    textViewWelcomeUser.text = "Olá, " + user.name
                }
            }.addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
            }
    }

    fun returnUser(dataSnapshot: DataSnapshot): User? {
        return dataSnapshot.getValue<User>()
    }

    fun signOut(view: View) {
        finish()
    }
}