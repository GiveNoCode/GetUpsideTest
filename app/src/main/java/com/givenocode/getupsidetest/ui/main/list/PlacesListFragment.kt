package com.givenocode.getupsidetest.ui.main.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.givenocode.getupsidetest.R
import com.givenocode.getupsidetest.ui.main.PlacesViewModel

class PlacesListFragment : Fragment() {

    companion object {
        fun newInstance() = PlacesListFragment()
    }

    private lateinit var viewModel: PlacesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_places_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(PlacesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}