package com.example.sharedpreferences.chat.activity.Register

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
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
    private lateinit var button: Button
    private lateinit var inputName: TextInputEditText
    private lateinit var inputEmail: TextInputEditText
    private lateinit var inputPassword: TextInputEditText
    private lateinit var inputRadioGroup: RadioGroup
    private lateinit var radioButton: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setView()
    }

    private fun setView() {
        inputName = findViewById(R.id.inputRegisterName)
        inputEmail = findViewById(R.id.inputRegisterEmail)
        inputPassword = findViewById(R.id.inputRegisterPassword)
        inputRadioGroup = findViewById(R.id.radioGroup)

        database = getDatabaseReference()
        auth = getFirebaseAuthentication()
        button = findViewById(R.id.btnSignUp)
        var actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
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

    private fun saveUser(userName: String, userEmail: String, password: String, userUid: String) {
        val userData = User().apply {
            name = userName
            email = userEmail
            userPassword = password
            userId = userUid
            gender = radioButton.text.toString()
        }
        userData.save()
    }

    fun singUp(view: View) {
        val name = inputName.text.toString()
        val email = inputEmail.text.toString()
        val password = inputPassword.text.toString()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Your account has been successfully created")
                    val userFirebase: FirebaseUser = task.result.user!!
                    saveUser(name, email, password, userFirebase.uid)
                    finish()
                } else {
                    Log.w(TAG, "Error:", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun getGender(view: View) {
        val radioId: Int = inputRadioGroup.checkedRadioButtonId
        radioButton = findViewById(radioId)
    }
}