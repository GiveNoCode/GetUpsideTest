package com.givenocode.getupsidetest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.givenocode.getupsidetest.App
import com.givenocode.getupsidetest.domain.PlacesRepository
import com.givenocode.network.ArcgisPlacesApi
import com.givenocode.getupsidetest.domain.model.Coordinates
import com.givenocode.getupsidetest.domain.model.Place
import kotlinx.coroutines.launch

class PlacesViewModel : ViewModel() {

    // TODO dependency injection
    private val placesRepository = PlacesRepository(
        ArcgisPlacesApi(),
        App.realmPlaceStorage
    )

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

            val data = placesRepository.findPlaces(coordinates)
            _placesLiveData.postValue(PlacesResource.Success(data))
        }

    }

    sealed class PlacesResource {
        object Idle : PlacesResource()
        object Loading : PlacesResource()
        class Success(val places: List<Place>) : PlacesResource()
    }

}