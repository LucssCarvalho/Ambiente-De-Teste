package com.example.sharedpreferences.chat.activity.Login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sharedpreferences.R
import com.example.sharedpreferences.chat.FirebaseConfig.FirebaseConfig
import com.example.sharedpreferences.chat.activity.Home.HomeActivity
import com.example.sharedpreferences.chat.activity.Signup.RegisterActivity
import com.example.sharedpreferences.chat.domain.User
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private val buttons = mutableListOf<Button>()
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var inputEmail: TextInputEditText
    private lateinit var inputPassword: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        buttons.add(findViewById(R.id.btnSignupNavigation))
        auth = Firebase.auth
        inputEmail = findViewById(R.id.inputLoginEmail)
        inputPassword = findViewById(R.id.inputLoginPassword)

        inputEmail.setText("lucas.carvalhocco@gmail.com")
        inputPassword.setText("lucas123")

        val currentUser = auth.currentUser
        if (currentUser != null) {
            startHomeActivity(getUser())
        }
    }

    fun startRegisterActivity(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent);
    }

    private fun startHomeActivity(userName: String) {
        if (userName.isNullOrEmpty()) {
            val intent = Intent(
                this,
                HomeActivity::class.java
            ).putExtra("userName", userName)
            startActivity(intent)
        }
    }

    private fun getUser(): String {
        var userName = ""
        database = FirebaseConfig.getDatabaseReference()
        database.child("users").child(auth.currentUser?.uid.toString()).get()
            .addOnSuccessListener {
                Log.i("firebase", "Got value ${it.value}")
                val user = returnUser(it)
                if (user != null) {
                    userName = user.name
                }
            }.addOnFailureListener {
                Log.e("firebase", "Error getting data", it)
            }
        return userName
    }

    private fun returnUser(dataSnapshot: DataSnapshot): User? {
        return dataSnapshot.getValue<User>()
    }

    fun signIn(view: View) {
        if (inputEmail.text.toString().isNullOrEmpty() &&
            inputPassword.text.toString().isNullOrEmpty()
        ) {
            auth.signInWithEmailAndPassword(
                inputEmail.text.toString(),
                inputPassword.text.toString()
            )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithEmail:success")
                        startHomeActivity(getUser())
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } else {
            Toast.makeText(
                baseContext, "fill in the fields",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
