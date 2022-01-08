package com.example.sharedpreferences.chat.domain

import com.example.sharedpreferences.chat.FirebaseConfig.FirebaseConfig
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class User {
    var userId: String = ""
    var name: String = ""
    var email: String = ""

    fun saveUser(){
       val databaseReference: DatabaseReference = FirebaseConfig.getDatabaseReference()
        databaseReference.child("users").child(userId).setValue(this)
    }
}
