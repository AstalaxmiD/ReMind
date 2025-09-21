package com.example.remind

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up toolbar if needed
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Get BottomNavigationView
        val bottomNavView: BottomNavigationView = findViewById(R.id.bottom_nav_view)

        // Set listener for item selection
        bottomNavView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nav_journal -> {
                    Toast.makeText(this, "Journal tapped", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, JournalActivity::class.java))
                    true
                }
                R.id.nav_reminders -> {
                    Toast.makeText(this, "Reminders tapped", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, RemindersActivity::class.java))
                    true
                }
                R.id.nav_capsule -> {
                    Toast.makeText(this, "Capsule tapped", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, CapsuleActivity::class.java))
                    true
                }
                R.id.nav_family_uploads -> {
                    Toast.makeText(this, "Family tapped", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, FamilyUploadsActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}
