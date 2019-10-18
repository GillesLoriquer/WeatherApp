package com.example.weatherapp.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.weatherapp.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

private const val MY_PERMISSION_ACCESS_COARSE_LOCATION = 1

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein by closestKodein()

    private lateinit var navController: NavController

    private val fusedLocationProviderClient: FusedLocationProviderClient by instance()

    // object : => like declaring an anonymous class
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            super.onLocationResult(p0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (hasLocationPermission()) {
            bindLocationManager()
            init()
        } else {
            requestLocationPersmission()
        }
    }

    // set up below has been delocalized from onCreate to prevent direct loading of current weather before
    // validation / invalidation of the user permission
    private fun init() {
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

    private fun requestLocationPersmission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            MY_PERMISSION_ACCESS_COARSE_LOCATION
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == MY_PERMISSION_ACCESS_COARSE_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bindLocationManager()
            } else {
                Toast.makeText(
                    this,
                    "Vous avez refusé la géolocalisation, rendez-vous dans 'Réglages, Localisation' pour " +
                            "définir la localisation souhaitée",
                    Toast.LENGTH_LONG
                ).show()
            }
            init()
        }
    }

    private fun bindLocationManager() {
        LifecycleBoundLocationManager(this, fusedLocationProviderClient, locationCallback)
    }

    private fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
}
