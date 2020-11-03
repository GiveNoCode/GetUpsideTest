package com.givenocode.getupsidetest.ui.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.givenocode.getupsidetest.R
import com.givenocode.getupsidetest.ui.PlacesViewModel
import kotlinx.android.synthetic.main.fragment_places_list.*

class PlacesListFragment : Fragment() {

    companion object {
        fun newInstance() = PlacesListFragment()
    }

    private lateinit var viewModel: PlacesViewModel

    private val adapter = PlacesListAdapter {
        viewModel.setSelectedPlace(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_places_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(PlacesViewModel::class.java)

        viewModel.placesLiveData.observe(viewLifecycleOwner) { result ->
            if (result is PlacesViewModel.PlacesResource.Success) {
                adapter.setItems(result.places)
            }
        }
    }

}