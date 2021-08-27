import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sharedpreferences.R
import com.example.sharedpreferences.SharePreferences.UserActivity
import com.example.sharedpreferences.Utils.FireBaseKeys.Companion.CHECKED
import com.example.sharedpreferences.Utils.FireBaseKeys.Companion.INPUT_NAME
import com.example.sharedpreferences.Utils.FireBaseKeys.Companion.SHARED_PREF
import com.example.sharedpreferences.Utils.FireBaseKeys.Companion.TIMER
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.installations.Utils
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences

    var isRemembered = false;

    private lateinit var button: Button
    private lateinit var inputName: TextInputEditText;
    lateinit var inputAge: TextInputEditText;
    lateinit var checked: CheckBox;

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeMaterialView()
        sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE)

        isRemembered = sharedPreferences.getBoolean(CHECKED, false)

        if (isRemembered) {
            startNewActivity()
        }

        val name: String = inputName.text.toString()
        val checked: Boolean = checked.isChecked



        button.setOnClickListener {

            Toast.makeText(this, "saved", Toast.LENGTH_LONG).show()
            savePreferences(name, checked)

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

    private fun validadeDate() {
        val dayInMillis = sharedPreferences.getLong("timer", 0)
        val dateNow = Calendar.getInstance()
        val lastCondigurationDate = Calendar.getInstance()
        lastCondigurationDate.apply {
            timeInMillis = dayInMillis
            add(Calendar.DAY_OF_YEAR, 30)
        }

        if (lastCondigurationDate.time > dateNow.time) {

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