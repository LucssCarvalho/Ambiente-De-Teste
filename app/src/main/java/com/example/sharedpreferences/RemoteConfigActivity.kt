package com.example.sharedpreferences

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.sharedpreferences.RemoteConfigUtils.enabledButton

class RemoteConfigActivity : AppCompatActivity() {

    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remote_config)

        (findViewById<Button>(R.id.button)).apply {
            text = RemoteConfigUtils.getHelloButtonText()
            setBackgroundColor(Color.parseColor(RemoteConfigUtils.getHelloButtonColor()))
            isEnabled = enabledButton()
        }
    }
}