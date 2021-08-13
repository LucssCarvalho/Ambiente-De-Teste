package com.example.sharedpreferences

import androidx.annotation.VisibleForTesting
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.remoteConfig
import org.json.JSONArray

object RemoteConfigUtils {
    private const val Tag = "RemoteConfigUtils"

    private const val HELLO_BUTTON_TEXT = "hello_button_text"
    private const val HELLO_BUTTON_COLOR = "hello_button_color"
    private const val ENABLE_BUTTON = "enable_button"
    private const val REMOTE_CONFIG_TIMEOUT_IN_SECONDS = 30L

    private val DEFAULTS: HashMap<String, Any> =
        hashMapOf(
            HELLO_BUTTON_TEXT to "Hello World!!!",
            HELLO_BUTTON_COLOR to "#0091FF",
            ENABLE_BUTTON to true
        )

    private lateinit var remoteConfig: FirebaseRemoteConfig

    fun init() {
        getFirebaseRemoteConfig()
    }

    @VisibleForTesting
    open fun getFirebaseRemoteConfig() {
        remoteConfig = Firebase.remoteConfig
        remoteConfig.apply {
            setDefaultsAsync(DEFAULTS)
            remoteConfig.setConfigSettingsAsync(
                FirebaseRemoteConfigSettings.Builder()
                    .setFetchTimeoutInSeconds(REMOTE_CONFIG_TIMEOUT_IN_SECONDS)
                    .build()
            )
        }
    }

    fun isEnabled(key: String): Boolean = remoteConfig.getBoolean(key)

    fun blackListAppsRemoteConfig(key: String): Array<String> {
        val blackListRemoteConfig = JSONArray(remoteConfig.getString(key))
        return Array(blackListRemoteConfig.length()) {
            blackListRemoteConfig.getString(it)
        }
    }
}
