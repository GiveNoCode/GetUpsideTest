package com.givenocode.getupsidetest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.givenocode.getupsidetest.data.PlacesRepository
import com.givenocode.getupsidetest.data.arcgis.ArcgisPlacesApi
import com.givenocode.getupsidetest.data.model.Coordinates
import com.givenocode.getupsidetest.data.model.Place
import kotlinx.coroutines.launch

class PlacesViewModel : ViewModel() {

    private val placesRepository = PlacesRepository(ArcgisPlacesApi())

    // don't expose mutable liveData out of ViewModel
    private val _deviceLocationLiveData = MutableLiveData<Coordinates>()
    val deviceLocationLiveData: LiveData<Coordinates> = _deviceLocationLiveData

    private val _placesLiveData = MutableLiveData<PlacesResource>().apply {
        postValue(PlacesResource.Idle)
    }
    val placesLiveData: LiveData<PlacesResource> = _placesLiveData

    fun setDeviceLocation(coordinates: Coordinates) {
        _deviceLocationLiveData.postValue(coordinates)
    }

    fun setSelectedLocation(coordinates: Coordinates) {
        viewModelScope.launch {
            _placesLiveData.postValue(PlacesResource.Loading)

            val data = placesRepository.findPlaces(coordinates.latitude, coordinates.longitude)
            _placesLiveData.postValue(PlacesResource.Success(data))
        }

    }

    sealed class PlacesResource {
        object Idle : PlacesResource()
        object Loading : PlacesResource()
        class Success(val places: List<Place>) : PlacesResource()
    }

}