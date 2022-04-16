package com.example.chatKotlin.chat.Model

import com.example.chatKotlin.chat.FirebaseConfig.FirebaseConfig
import com.example.chatKotlin.chat.FirebaseConfig.FirebaseUtils.FIREBASE_TAG_USERS
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Exclude

class User {
    @Exclude
    var userId: String = ""
    var name: String = ""

    @Exclude
    var email: String = ""

    fun save() {
        val databaseReference: DatabaseReference = FirebaseConfig.getDatabaseReference()
        databaseReference.child(FIREBASE_TAG_USERS).child(userId).setValue(this)
    }
}
