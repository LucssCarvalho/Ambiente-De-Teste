package com.example.sharedpreferences

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class RemoteConfigActivity : AppCompatActivity() {

    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remote_config)

        (findViewById<Button>(R.id.button)).apply {
            isEnabled = RemoteConfigUtils.isEnabled("enable_button")
        }
    }
}