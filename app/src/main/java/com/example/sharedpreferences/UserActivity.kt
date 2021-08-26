package com.example.sharedpreferences

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.sharedpreferences.Utils.Companion.INPUT_AGE
import com.example.sharedpreferences.Utils.Companion.INPUT_NAME
import com.example.sharedpreferences.Utils.Companion.SHARED_PREF
import com.example.sharedpreferences.Utils.Companion.TIMER
import java.util.*

class UserActivity : AppCompatActivity() {

    private lateinit var preferences: SharedPreferences
    lateinit var tvName: TextView;
    lateinit var tvAge: TextView;
    lateinit var tvTimer: TextView;
    lateinit var button: Button

    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        preferences = getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)

        initializeMaterialView()

        val name = preferences.getString(INPUT_NAME, "sem nome");
        val age = preferences.getInt(INPUT_AGE, 0);
        val myDate = Date(preferences.getLong(TIMER, 0))
        tvName.text = name;
        tvAge.text = "" + age
        tvTimer.text = myDate.toString()

        button.setOnClickListener {
            val editor: SharedPreferences.Editor = preferences.edit()
            editor.clear()
            editor.apply()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent);
            finish()
        }


    }

    private fun initializeMaterialView() {
        tvName = findViewById(R.id.userName)
        tvAge = findViewById(R.id.age)
        button = findViewById(R.id.logoutButton);
        tvTimer = findViewById(R.id.tvTimer)
    }
}