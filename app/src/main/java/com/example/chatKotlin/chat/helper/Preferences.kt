package com.example.chatKotlin.chat.helper

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.database.ValueEventListener

class Preferences(context: Context) {

    private var CURRENT_USER_ID = "current_user_id"
    private var CHAT_FIREBASE_ID = "chat_firebase"
    private var editor: SharedPreferences.Editor
    private var preferences: SharedPreferences
    private var MODE = 0

    init {
        preferences = context.getSharedPreferences(CHAT_FIREBASE_ID, MODE)
        editor = preferences.edit()
    }

    fun saveUserData(userId: String) {
        editor.putString(CURRENT_USER_ID, userId)
        editor.commit()
    }

    fun getIdentification() = preferences.getString(CURRENT_USER_ID, null)

}