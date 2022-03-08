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
import com.example.sharedpreferences.chat.activity.Home.HomeActivity
import com.example.sharedpreferences.chat.activity.Signup.RegisterActivity
import com.example.sharedpreferences.chat.domain.User
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private val buttons = mutableListOf<Button>()
    private lateinit var auth: FirebaseAuth
    private lateinit var inputEmail: TextInputEditText
    private lateinit var inputPassword: TextInputEditText
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        buttons.add(findViewById(R.id.btnSignupNavigation))
        auth = Firebase.auth
        inputEmail = findViewById(R.id.inputLoginEmail)
        inputPassword = findViewById(R.id.inputLoginPassword)

        inputEmail.setText("lucas@gmail.com.br")
        inputPassword.setText("123456")

        validateCurrentUser()
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

    fun signIn(view: View) {
        user = User().apply {
            email = inputEmail.text.toString()
            userPassword = inputPassword.text.toString()
        }
        if (user.email.isNotEmpty() && user.userPassword.isNotEmpty()) {
            auth.signInWithEmailAndPassword(user.email, user.userPassword)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
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
}
