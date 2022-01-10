package com.example.sharedpreferences.SharePreferences

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sharedpreferences.R
import com.example.sharedpreferences.Utils.FireBaseKeys.Companion.CHECKED
import com.example.sharedpreferences.Utils.FireBaseKeys.Companion.INPUT_NAME
import com.example.sharedpreferences.Utils.FireBaseKeys.Companion.SHARED_PREF
import com.google.android.material.textfield.TextInputEditText

class SharePreferences : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences

    private var isRemembered = false;

    private lateinit var button: Button
    private lateinit var inputName: TextInputEditText;
    lateinit var checked: CheckBox;
    var nameValue: String = ""
    var checkedValue: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE)

        isRemembered = sharedPreferences.getBoolean(CHECKED, false)

        if (isRemembered) {
            startNewActivity()
        }

        nameValue = inputName.text.toString()
        checkedValue = checked.isChecked

        button.setOnClickListener {
            nameValue = inputName.text.toString()
            checkedValue = checked.isChecked

            Toast.makeText(this, "saved", Toast.LENGTH_LONG).show()
            savePreferences(nameValue, checkedValue)
        }
    }

    private fun savePreferences(name: String, checked: Boolean) {
        sharedPreferences.edit().apply {
            putString(INPUT_NAME, name);
            putBoolean(CHECKED, checked);
            apply()
        }
        startNewActivity()
    }

    private fun startNewActivity() {
        val intent = Intent(this, UserActivity::class.java)
        startActivity(intent);
        finish()
    }

    fun signOut(view: View) {}
}