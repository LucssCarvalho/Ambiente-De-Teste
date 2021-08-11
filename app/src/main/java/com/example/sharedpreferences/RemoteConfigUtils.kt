package com.example.sharedpreferences

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

object RemoteConfigUtils {
    private const val Tag = "RemoteConfigUtils"

    private const val HELLO_BUTTON_TEXT = "hello_button_text"
    private const val HELLO_BUTTON_COLOR = "hello_button_color"
    private const val ENABLE_BUTTON = "enable_button"

    private val DEFAULTS: HashMap<String, Any> =
        hashMapOf(
            HELLO_BUTTON_TEXT to "Hello World!!!",
            HELLO_BUTTON_COLOR to "#0091FF"
        )

    private lateinit var remoteConfig: FirebaseRemoteConfig

    fun init() {
        remoteConfig = getFirebaseRemoteConfig()
    }

    private fun getFirebaseRemoteConfig(): FirebaseRemoteConfig {
        val remoteConfig = Firebase.remoteConfig

        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = if (BuildConfig.DEBUG) {
                0
            } else {
                60 * 60
            }
        }

        remoteConfig.apply {
            setConfigSettingsAsync(configSettings)
            setDefaultsAsync(DEFAULTS)
            fetchAndActivate().addOnCompleteListener {
                Log.d(TAG, "Remote Config Fetch Complete")
            }
        }
        return remoteConfig
    }

    fun getHelloButtonText(): String = remoteConfig.getString(HELLO_BUTTON_TEXT)

    fun getHelloButtonColor(): String = remoteConfig.getString(HELLO_BUTTON_COLOR)

    fun enabledButton(): Boolean = remoteConfig.getBoolean(ENABLE_BUTTON)
}