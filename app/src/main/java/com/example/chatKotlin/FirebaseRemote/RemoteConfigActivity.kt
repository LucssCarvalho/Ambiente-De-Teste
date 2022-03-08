package com.example.chatKotlin.FirebaseRemote

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.chatKotlin.R

class RemoteConfigActivity : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var tvDays: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remote_config)

        (findViewById<TextView>(R.id.tvDays)).apply {
            val day = RemoteConfigUtils.getDays() + 20
            text = day.toString()
        }

//        (findViewById<Button>(R.id.button)).apply {
//            isEnabled = RemoteConfigUtils.isEnabled(ENABLE_BUTTON)
//        }
    }
}