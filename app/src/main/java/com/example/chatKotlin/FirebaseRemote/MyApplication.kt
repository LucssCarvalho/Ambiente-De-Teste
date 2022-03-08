package com.example.chatKotlin.FirebaseRemote

import android.app.Application

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        RemoteConfigUtils.init()
    }
}