package com.givenocode.getupsidetest.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.givenocode.getupsidetest.R
import com.givenocode.getupsidetest.ui.main.list.PlacesListFragment
import com.givenocode.getupsidetest.ui.main.map.PlacesMapFragment
import java.lang.IllegalArgumentException

private val TAB_TITLES = arrayOf(
    R.string.tab_text_map,
    R.string.tab_text_list
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return when (position) {
            0 -> PlacesMapFragment.newInstance()
            1 -> PlacesListFragment.newInstance()
            else -> throw IllegalArgumentException("tab $position not implemented")
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 2
    }
}