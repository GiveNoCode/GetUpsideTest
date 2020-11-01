package com.givenocode.getupsidetest.ui

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.givenocode.getupsidetest.R
import com.givenocode.getupsidetest.data.Coordinates
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: PlacesViewModel

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                checkPermission()
            }
        }

    private var alertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(PlacesViewModel::class.java)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val tabsAdapter = TabsAdapter(this)
        viewPager.adapter = tabsAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.setText(R.string.tab_text_map)
                1 -> tab.setText(R.string.tab_text_list)
                else -> throw IllegalArgumentException("position $position not supported")
            }
        }.attach()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                // disable viewpager swipe to allow full Map control
                viewPager.isUserInputEnabled = position != 0
            }
        })

        viewModel.placesLiveData.observe(this) {
            progressBar.visibility = if (it is PlacesViewModel.PlacesResource.Loading) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    override fun onStart() {
        super.onStart()
        checkGooglePlayServices()
    }

    private fun checkGooglePlayServices() {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val googlePlaySetupResult = apiAvailability.isGooglePlayServicesAvailable(this)
        if (googlePlaySetupResult == ConnectionResult.SUCCESS) {
            checkPermission()
        } else {
            apiAvailability.getErrorDialog(this, googlePlaySetupResult, 0)
                .show()
        }
    }

    private fun checkPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            viewModel.setDeviceLocation(Coordinates(location.latitude, location.longitude))
                        } else {
                            Snackbar.make(
                                coordinatorLayout,
                                R.string.location_unavailable_message,
                                Snackbar.LENGTH_INDEFINITE
                            )
                                .setAction(R.string.retry_button_label) {
                                    checkPermission()
                                }
                                .show()
                        }
                    }
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) -> {
                alertDialog?.dismiss()
                alertDialog = AlertDialog.Builder(this)
                    .setMessage(R.string.location_permission_rationale)
                    .setPositiveButton(android.R.string.ok) { _, _ ->
                        requestPermissionLauncher.launch(
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    }
                    .setNegativeButton(android.R.string.cancel) { _, _ -> }
                    .show()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
        }
    }

}