package com.example.chatKotlin.chat.activity.Home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.example.chatKotlin.R
import com.example.chatKotlin.chat.Adapter.TabAdapter
import com.example.chatKotlin.chat.FirebaseConfig.FirebaseConfig
import com.example.chatKotlin.chat.activity.Login.LoginActivity
import com.example.chatKotlin.chat.helper.SlidingTabLayout
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var slidingTabLayout: SlidingTabLayout
    private lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        auth = FirebaseConfig.getFirebaseAuthentication()

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = "\uD83E\uDDA7"

        slidingTabLayout = findViewById(R.id.stl_tabs)
        viewPager = findViewById(R.id.vp_page)

        slidingTabLayout.setDistributeEvenly(true)
        slidingTabLayout.setSelectedIndicatorColors(
            ContextCompat.getColor(this, R.color.colorAccent))

        var tabadapter = TabAdapter(supportFragmentManager)
        viewPager.adapter = tabadapter

        slidingTabLayout.setViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            Toast.makeText(this, "Settings", Toast.LENGTH_LONG).show()
            true
        }
        R.id.item_logout -> {
            signOut()
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun signOut() {
        auth.signOut()
        returnLoginActivity()
    }

    private fun returnLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}