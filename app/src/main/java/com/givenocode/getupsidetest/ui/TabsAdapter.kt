package com.givenocode.getupsidetest.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.givenocode.getupsidetest.ui.list.PlacesListFragment
import com.givenocode.getupsidetest.ui.map.PlacesMapFragment

class TabsAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PlacesMapFragment.newInstance()
            1 -> PlacesListFragment.newInstance()
            else -> throw IllegalArgumentException("tab $position not implemented")
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}