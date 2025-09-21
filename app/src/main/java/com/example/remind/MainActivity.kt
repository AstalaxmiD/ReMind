package com.example.remind

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.remind.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup toolbar
        setSupportActionBar(binding.appBarMain.toolbar)

        // Setup FAB
        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }

        // Find NavHostFragment
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_content_main) as? NavHostFragment
        val navController = navHostFragment?.navController

        if (navController != null) {
            // Top-level destinations
            val topLevelDestinations = setOf(
                R.id.nav_journal,
                R.id.nav_slideshow,
                R.id.nav_settings,
                R.id.nav_reflow,
                R.id.nav_FamilyUploads
            )

            appBarConfiguration = AppBarConfiguration(topLevelDestinations, binding.drawerLayout)
            setupActionBarWithNavController(navController, appBarConfiguration)

            // Drawer navigation
            binding.navView.setupWithNavController(navController)

            // Bottom navigation
            binding.appBarMain.contentMain.bottomNavView.setupWithNavController(navController)
        } else {
            Log.e("MainActivity", "NavHostFragment not found! Check layout.")
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
