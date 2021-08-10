package com.example.sharedpreferences

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sharedpreferences.Utils.Companion.CHECKED
import com.example.sharedpreferences.Utils.Companion.INPUT_AGE
import com.example.sharedpreferences.Utils.Companion.INPUT_NAME
import com.example.sharedpreferences.Utils.Companion.SHARED_PREF
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences

    var isRemembered = false;

    private lateinit var button: Button
    private lateinit var inputName: TextInputEditText;
    lateinit var inputAge: TextInputEditText;
    lateinit var checked: CheckBox;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeMaterialView()
        sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE)

        isRemembered = sharedPreferences.getBoolean(CHECKED, false)

        if (isRemembered) {
            startNewActivity()
        }

        button.setOnClickListener {
            val name: String = inputName.text.toString()
            val age: Int = inputAge.text.toString().toInt()
            val checked: Boolean = checked.isChecked

            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString(INPUT_NAME, name);
            editor.putInt(INPUT_AGE, age)
            editor.putBoolean(CHECKED, checked);
            editor.apply()

            Toast.makeText(this, "saved", Toast.LENGTH_LONG).show()
            startNewActivity()
        }
    }

    private fun initializeMaterialView() {
        button = findViewById(R.id.loginButton);
        inputName = findViewById(R.id.inputName)
        inputAge = findViewById(R.id.inputAge)
        checked = findViewById(R.id.checkBox)
    }

    private fun startNewActivity() {
        val intent = Intent(this, UserActivity::class.java)
        startActivity(intent);
        finish()
    }
}