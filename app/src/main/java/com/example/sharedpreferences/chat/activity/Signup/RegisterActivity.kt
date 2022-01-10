package com.example.sharedpreferences.chat.activity.Signup

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
import com.google.firebase.auth.*
import com.google.firebase.database.DatabaseReference

class RegisterActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var button: Button
    private lateinit var inputName: TextInputEditText
    private lateinit var inputEmail: TextInputEditText
    private lateinit var inputPassword: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setView()
    }

    private fun setView() {
        inputName = findViewById(R.id.inputRegisterName)
        inputName.error = "You need to enter a name"

        inputEmail = findViewById(R.id.inputRegisterEmail)
        inputEmail.error = "You need to enter a email address"

        inputPassword = findViewById(R.id.inputRegisterPassword)
        inputPassword.error = "You need to enter a password"

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
        }
        userData.save()
    }

    fun singUp(view: View) {
        val name = inputName.text.toString()
        val email = inputEmail.text.toString()
        val password = inputPassword.text.toString()

        if (name != "" && email != "" && password != "") {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Your account has been successfully created")
                        val userFirebase: FirebaseUser = task.result.user!!
                        saveUser(name, email, password, userFirebase.uid)
                        auth.signOut()
                        finish()
                    } else {
                        var exception: String = try {
                            throw task.exception!!
                        } catch (e: FirebaseAuthWeakPasswordException) {
                            "Password not strong enough! Must be at least 6 characters"
                        } catch (e: FirebaseAuthInvalidCredentialsException) {
                            "Please use a valid email address"
                        } catch (e: FirebaseAuthUserCollisionException) {
                            "This e-mail address has already registered"
                        } catch (e: Exception) {
                            "Error registering user"
                        }
                        Log.w(TAG, "Error:", task.exception)
                        Toast.makeText(
                            baseContext, "$exception",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } else {
            Toast.makeText(
                baseContext, "Please complete all required fields!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
