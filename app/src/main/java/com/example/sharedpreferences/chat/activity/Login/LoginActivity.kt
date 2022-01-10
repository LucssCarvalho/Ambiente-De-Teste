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
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private val buttons = mutableListOf<Button>()
    private lateinit var auth: FirebaseAuth
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
            startHomeActivity()
        }
    }

    fun startRegisterActivity(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent);
    }

    private fun startHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent);
    }

    fun signIn(view: View) {
        if (inputEmail.text.toString() != null && inputEmail.text.toString() != "" &&
            inputPassword.text.toString() != null && inputPassword.text.toString() != ""
        ) {
            auth.signInWithEmailAndPassword(
                inputEmail.text.toString(),
                inputPassword.text.toString()
            )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = auth.currentUser
                        startHomeActivity()
                    } else {
                        // If sign in fails, display a message to the user.
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
