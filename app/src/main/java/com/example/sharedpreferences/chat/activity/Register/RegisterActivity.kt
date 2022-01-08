package com.example.sharedpreferences.chat.activity.Register

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sharedpreferences.R
import com.example.sharedpreferences.chat.FirebaseConfig.FirebaseConfig.Companion.getDatabaseReference
import com.example.sharedpreferences.chat.FirebaseConfig.FirebaseConfig.Companion.getFirebaseAuthentication
import com.example.sharedpreferences.chat.domain.User
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference

class RegisterActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var users: DatabaseReference
    private lateinit var button: Button
    private lateinit var inputName: TextInputEditText
    private lateinit var inputEmail: TextInputEditText
    private lateinit var inputPassword: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        inputName = findViewById(R.id.inputRegisterName)
        inputEmail = findViewById(R.id.inputRegisterEmail)
        inputPassword = findViewById(R.id.inputRegisterPassword)

        database = getDatabaseReference()
        auth = getFirebaseAuthentication()
        users = database.child("users")
        button = findViewById(R.id.btnSignUp)

        var actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.common_google_signin_btn_icon_dark);
            actionBar.setHomeButtonEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveUser(userName: String, userEmail: String, userUid: String) {
        val userData = User().apply {
            name = userName
            email = userEmail
            userId = userUid
        }
        userData.saveUser()
    }

    fun singUp(view: View) {
        auth.createUserWithEmailAndPassword(
            inputEmail.text.toString(),
            inputPassword.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    val userFirebase: FirebaseUser = task.result.user!!
                    saveUser(
                        inputName.text.toString(),
                        inputEmail.text.toString(),
                        userFirebase.uid
                    )
                    finish()
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}