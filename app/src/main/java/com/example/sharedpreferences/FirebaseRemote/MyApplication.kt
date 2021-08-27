package com.example.sharedpreferences.FirebaseRemote

import android.app.Application
import com.example.sharedpreferences.FirebaseRemote.RemoteConfigUtils

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        RemoteConfigUtils.init()
    }
}