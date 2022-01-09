package com.example.sharedpreferences.chat.domain
import com.example.sharedpreferences.chat.FirebaseConfig.FirebaseConfig
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Exclude

class User {
    @Exclude
    var userId: String = ""
    var name: String = ""
    @Exclude
    var userPassword: String = ""
    var email: String = ""
    var gender: String = ""

    fun save(){
       val databaseReference: DatabaseReference = FirebaseConfig.getDatabaseReference()
        databaseReference.child("users").child(userId).setValue(this)
    }
}
