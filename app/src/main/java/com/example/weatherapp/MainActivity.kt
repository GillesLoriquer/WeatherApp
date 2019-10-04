package com.example.weatherapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Sets the toolbar as the app bar for the activity
        setSupportActionBar(findViewById(R.id.toolbar))

        // Gets the NavController from the activity
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        // TOOLBAR
        // Sets up the ActionBar returned by AppCompatActivity.getSupportActionBar() for use with a NavController
        NavigationUI.setupActionBarWithNavController(this, navController)

        // BOTTOM NAVIGATION
        // Sets up a BottomNavigationView for use with a NavController
        // Extension function => NavigationUI.setupWithNavController(bottom_nav, navController)
        bottom_nav.setupWithNavController(navController)
    }

    // This method is called whenever the user chooses to navigate Up within your application's activity hierarchy
    // from the action bar.
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }
}
