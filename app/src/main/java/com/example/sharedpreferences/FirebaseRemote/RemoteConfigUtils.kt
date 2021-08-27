package com.example.sharedpreferences.FirebaseRemote

import com.example.sharedpreferences.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.remoteConfig

object RemoteConfigUtils {
    private const val Tag = "RemoteConfigUtils"

    private lateinit var remoteConfig: FirebaseRemoteConfig

    private const val REMOTE_CONFIG_TIMEOUT_IN_SECONDS = 30L
    private const val REMOTE_CONFIG_CACHE_EXPIRATION = 10L

    fun init() {
        getFirebaseRemoteConfig()
        fetchData()
    }

    private fun getFirebaseRemoteConfig() {
        remoteConfig = Firebase.remoteConfig
        remoteConfig.apply {
            setDefaultsAsync(R.xml.default_remote)
            setConfigSettingsAsync(
                FirebaseRemoteConfigSettings.Builder()
                    .setFetchTimeoutInSeconds(REMOTE_CONFIG_TIMEOUT_IN_SECONDS)
                    .build()
            )
        }
    }

    private fun fetchData() {
        with(remoteConfig) {
            fetch(REMOTE_CONFIG_CACHE_EXPIRATION).addOnCompleteListener { activate() }
            activate()
        }
    }

    fun getHelloButtonText(): String = remoteConfig.getString(RemoteConfigKeys.ENABLE_BUTTON.toString())

    fun getDays(): Int = remoteConfig.getString(RemoteConfigKeys.DAYS_FIREBASE.toString()).toInt()

    fun getHelloButtonColor(): String = remoteConfig.getString(RemoteConfigKeys.DAYS_FIREBASE.toString())
}