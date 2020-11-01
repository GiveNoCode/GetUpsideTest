package com.givenocode.getupsidetest.ui

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.givenocode.getupsidetest.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.IllegalArgumentException

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: PlacesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabsAdapter = TabsAdapter(this)
        viewPager.adapter = tabsAdapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position){
                0 -> tab.setText(R.string.tab_text_map)
                1 -> tab.setText(R.string.tab_text_list)
                else -> throw IllegalArgumentException("position $position not supported")
            }
        }.attach()

        viewModel = ViewModelProvider(this).get(PlacesViewModel::class.java)

    }

    override fun onStart() {
        super.onStart()
        checkPermission()
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                requestLocation()
            }
        }

    private fun checkPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                requestLocation()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) -> {
                AlertDialog.Builder(this)
                    .setMessage(R.string.location_permission_rationale)
                    .setPositiveButton(android.R.string.ok) { _, _ -> requestPermissionLauncher.launch(
                        Manifest.permission.ACCESS_COARSE_LOCATION) }
                    .setNegativeButton(android.R.string.cancel) { _, _ -> }
                    .show()
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
        }
    }

    private fun requestLocation() {

    }
}