package com.givenocode.getupsidetest.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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

        viewPager.registerOnPageChangeCallback(object  : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when(position) {
                    0 -> viewModel.setDisplayMode(DisplayMode.Map)
                    1 -> viewModel.setDisplayMode(DisplayMode.List)
                    else -> throw IllegalArgumentException("position $position not supported")
                }
            }
        })

        viewModel = ViewModelProvider(this).get(PlacesViewModel::class.java)

        initSubscription()
    }

    private fun initSubscription(){
        viewModel.displayModeLiveData.observe(this) { displayMode ->
            when(displayMode) {
                DisplayMode.Map -> {
                    viewPager.setCurrentItem(0, true)
                }
                DisplayMode.List -> {
                    viewPager.setCurrentItem(1, true)
                }
            }
        }
    }
}