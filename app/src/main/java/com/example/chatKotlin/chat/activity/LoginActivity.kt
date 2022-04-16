package com.example.chatKotlin.chat.Activity

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.chatKotlin.R
import com.example.chatKotlin.chat.FirebaseConfig.FirebaseConfig
import com.example.chatKotlin.chat.Model.User
import com.example.chatKotlin.chat.helper.Base64Custom
import com.example.chatKotlin.chat.helper.Preferences
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private val buttons = mutableListOf<Button>()
    private lateinit var auth: FirebaseAuth
    private lateinit var inputEmail: TextInputEditText
    private lateinit var inputPassword: TextInputEditText
    private lateinit var valueEventListener: ValueEventListener
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createView()

        inputEmail.setText("lucas@gmail.com")
        inputPassword.setText("123456")

        validateCurrentUser()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun signIn(view: View) {
        val email = inputEmail.text.toString()
        val password = inputPassword.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        createUserReference(email)

                        Log.d(TAG, "signInWithEmail:success")
                        Toast.makeText(this, "Successful login", Toast.LENGTH_LONG).show()
                        startHomeActivity()
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_LONG)
                            .show()
                    }
                }
        } else {
            Toast.makeText(baseContext, "fill in the fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveUserPreferences(userId: String, userName: String) {
        val preferences = Preferences(this)
        preferences.saveUserData(userId, userName)
    }

    private fun createView() {
        setContentView(R.layout.activity_login)
        buttons.add(findViewById(R.id.btnSignupNavigation))
        auth = Firebase.auth
        inputEmail = findViewById(R.id.inputLoginEmail)
        inputPassword = findViewById(R.id.inputLoginPassword)
    }

    fun startRegisterActivity(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent);
    }

    private fun startHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun validateCurrentUser() {
        if (auth.currentUser != null) startHomeActivity()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createUserReference(email: String) {
        val userCurrentId = Base64Custom().encodeBase64(email)

        database =
            FirebaseConfig.getDatabaseReference().child("users")
                .child(userCurrentId)

        valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val currentUser: User = snapshot.getValue(User::class.java) as User
                saveUserPreferences(userCurrentId, currentUser.name)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        database.addValueEventListener(valueEventListener)
    }
}
