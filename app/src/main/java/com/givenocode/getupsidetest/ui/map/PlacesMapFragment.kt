package com.givenocode.getupsidetest.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.givenocode.getupsidetest.R
import com.givenocode.getupsidetest.data.Coordinates
import com.givenocode.getupsidetest.ui.PlacesViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

class PlacesMapFragment : Fragment() {

    companion object {

        private const val CAMERA_ZOOM_LEVEL = 14f

        fun newInstance() = PlacesMapFragment()
    }


    private lateinit var viewModel: PlacesViewModel
    private var googleMap: GoogleMap? = null

    private val callback = OnMapReadyCallback { googleMap ->
        this.googleMap = googleMap
        if (this::viewModel.isInitialized) {
            viewModel.deviceLocationLiveData.value?.let { location ->
                updateMap(location)
            }
        }

        googleMap.setOnCameraIdleListener {
            val target = googleMap.cameraPosition.target
            viewModel.setSelectedLocation(Coordinates(target.latitude, target.longitude))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_places_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(PlacesViewModel::class.java)

        viewModel.deviceLocationLiveData.observe(viewLifecycleOwner) {
            updateMap(it)
        }
    }

    private fun updateMap(coordinates: Coordinates) {
        val currentLatLng = LatLng(coordinates.latitude, coordinates.longitude)
        val cameraPosition = CameraPosition.Builder()
            .zoom(CAMERA_ZOOM_LEVEL)
            .target(currentLatLng)
            .build()
        googleMap?.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }
}