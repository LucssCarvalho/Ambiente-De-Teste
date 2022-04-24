package com.example.chatKotlin.chat.helper

import android.content.Context
import android.content.SharedPreferences

class Preferences(context: Context) {

    private var CURRENT_USER_ID = "current_user_id"
    private var CURRENT_USER_NAME = "current_user_name"
    private var CHAT_FIREBASE_ID = "chat_firebase"
    private var CURRENT_STATUS = "current_status"
    private var editor: SharedPreferences.Editor
    private var preferences: SharedPreferences
    private var MODE = 0

    init {
        preferences = context.getSharedPreferences(CHAT_FIREBASE_ID, MODE)
        editor = preferences.edit()
    }

    fun saveUserData(userId: String, userName: String) {
        editor.putString(CURRENT_USER_ID, userId)
        editor.putString(CURRENT_USER_NAME, userName)
        editor.commit()
    }

    fun saveStatus(status: String) {
        editor.putString(CURRENT_STATUS, status)
        editor.commit()
    }

    fun getIdentification() = preferences.getString(CURRENT_USER_ID, null)

    fun getStatus() = preferences.getString(CURRENT_STATUS, null)

    fun getName() = preferences.getString(CURRENT_USER_NAME, null)
}
