package com.example.sharedpreferences

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class UserActivity : AppCompatActivity() {

    private lateinit var preferences: SharedPreferences
    lateinit var viewName: TextView;
    lateinit var viewAge: TextView;
    lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        preferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)

        viewName = findViewById(R.id.userName)
        viewAge = findViewById(R.id.age)
        button = findViewById(R.id.logoutButton);


        val name = preferences.getString("NAME", "");
        viewName.text = name;
        val age = preferences.getInt("AGE", 0);
        viewAge.text = "" + age

        button.setOnClickListener {
            val editor: SharedPreferences.Editor = preferences.edit()
            editor.clear()
            editor.apply()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent);
            finish()
        }

    }
}