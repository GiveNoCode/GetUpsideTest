package com.givenocode.getupsidetest.ui.map

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.givenocode.getupsidetest.R
import com.givenocode.getupsidetest.domain.model.Place
import com.givenocode.getupsidetest.domain.model.SearchLocation
import com.givenocode.getupsidetest.ui.PlacesViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions


class PlacesMapFragment : Fragment() {

    companion object {

        private const val CAMERA_ZOOM_LEVEL = 14f

        fun newInstance() = PlacesMapFragment()
    }


    private lateinit var viewModel: PlacesViewModel
    private var googleMap: GoogleMap? = null

    private var enableCameraListener = true

    private val handler = Handler(Looper.getMainLooper())

    private val mapReadyCallback = OnMapReadyCallback { googleMap ->
        this.googleMap = googleMap
        if (this::viewModel.isInitialized) {
            viewModel.searchLocationLiveData.value?.let { location ->
                updateMap(location)
            }
        }

        googleMap.setOnCameraIdleListener {
            if (enableCameraListener) {
                val target = googleMap.cameraPosition.target
                viewModel.setSelectedLocation(SearchLocation(target.latitude, target.longitude))
            }
        }

        googleMap.setOnMarkerClickListener { marker ->
            val place = (marker.tag as? Place)
            if (place == null) {
                false
            } else {
                viewModel.setSelectedPlace(place)
                true
            }
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
        mapFragment?.getMapAsync(mapReadyCallback)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(PlacesViewModel::class.java)

        viewModel.searchLocationLiveData.observe(viewLifecycleOwner) {
            updateMap(it)
        }

        viewModel.placesLiveData.observe(viewLifecycleOwner) { result ->
            if (result is PlacesViewModel.PlacesResource.Success) {
                googleMap?.clear()

                val boundsBuilder = LatLngBounds.Builder()

                result.places.forEach {place ->
                    val latLng = LatLng(place.latitude, place.longitude)
                    googleMap?.addMarker(MarkerOptions().position(latLng))?.apply {
                        tag = place
                    }

                    boundsBuilder.include(latLng)
                }
                val bounds = boundsBuilder.build()

                val cu = CameraUpdateFactory.newLatLngBounds(
                    bounds, resources.getDimensionPixelOffset(
                        R.dimen.map_padding
                    )
                )

                enableCameraListener = false
                googleMap?.animateCamera(cu, object : GoogleMap.CancelableCallback {
                    override fun onFinish() {
                        handler.postDelayed({
                            enableCameraListener = true
                        }, 500)
                    }

                    override fun onCancel() {
                        handler.postDelayed({
                            enableCameraListener = true
                        }, 500)
                    }

                })
            }
        }
    }

    private fun updateMap(searchLocation: SearchLocation) {
        val currentLatLng = LatLng(searchLocation.latitude, searchLocation.longitude)
        val cameraPosition = CameraPosition.Builder()
            .zoom(CAMERA_ZOOM_LEVEL)
            .target(currentLatLng)
            .build()
        googleMap?.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }
}