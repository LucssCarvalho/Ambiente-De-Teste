package com.example.chatKotlin.chat.activity

import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.chatKotlin.R
import com.example.chatKotlin.chat.FirebaseConfig.FirebaseConfig
import com.example.chatKotlin.chat.FirebaseConfig.FirebaseUtils
import com.example.chatKotlin.chat.helper.Preferences
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class SettingsActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var statusButton: LinearLayout
    private lateinit var nameTextView: TextView
    private lateinit var statusTextView: TextView
    private lateinit var firebaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        toolbar = findViewById(R.id.tb_settings)
        toolbar.title = "Settings"
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        setSupportActionBar(toolbar)

        nameTextView = findViewById(R.id.tv_name_settings)
        statusTextView = findViewById(R.id.tv_status_settings)

        val preferences = Preferences(this)
        nameTextView.text = preferences.getName().toString()
        statusTextView.text = preferences.getStatus().toString()

        statusButton = findViewById(R.id.btn_set_status)

        statusButton.setOnClickListener {
            updateStatus()
        }
    }

    private fun updateStatus() {
        val editUpdateStatus = EditText(this)
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this).apply {
            setTitle("Update Status")
            setMessage("send yout status")
            setCancelable(false)
            setView(editUpdateStatus)
        }

        alertDialog.setPositiveButton("update status") { _, _ ->
            run {
                val statusMessage = editUpdateStatus.text.toString()
                val context = this
                val preferences = Preferences(context)
                val userIdCurrentUser: String =
                    preferences.getIdentification().toString()

                firebaseReference =
                    FirebaseConfig.getDatabaseReference()
                        .child(FirebaseUtils.FIREBASE_TAG_USERS)
                        .child(userIdCurrentUser)
                        .child("status")

                if (statusMessage.isEmpty()) {
                    Toast.makeText(this, "fill in the status", Toast.LENGTH_LONG)
                } else {
                    preferences.saveStatus(statusMessage)
                    firebaseReference.setValue(statusMessage)
                    statusTextView.text = statusMessage
                }
            }
            alertDialog.setNegativeButton("Cancel") { _, _ -> run {} }
        }
        alertDialog.create()
        alertDialog.show()
    }
}