package com.example.sharedpreferences.chat.FirebaseConfig

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirebaseConfig {

    companion object {
        fun getDatabaseReference(): DatabaseReference {
            return FirebaseDatabase.getInstance().getReference()
        }

        fun getFirebaseAuthentication(): FirebaseAuth {
            return FirebaseAuth.getInstance()
        }
    }
}
