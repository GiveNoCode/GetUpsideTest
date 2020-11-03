package com.givenocode.getupsidetest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.givenocode.getupsidetest.App
import com.givenocode.getupsidetest.domain.PlacesRepository
import com.givenocode.network.ArcgisPlacesApi
import com.givenocode.getupsidetest.domain.model.SearchLocation
import com.givenocode.getupsidetest.domain.model.Place
import kotlinx.coroutines.launch

class PlacesViewModel : ViewModel() {

    // TODO dependency injection
    private val placesRepository = PlacesRepository(
        ArcgisPlacesApi(),
        App.realmPlaceStorage
    )

    // don't expose mutable liveData out of ViewModel
    private val _deviceLocationLiveData = MutableLiveData<SearchLocation>()
    val searchLocationLiveData: LiveData<SearchLocation> = _deviceLocationLiveData

    private val _selectedPlaceLiveData = MutableLiveData<Place?>()
    val selectedPlaceLiveData: LiveData<Place?> = _selectedPlaceLiveData

    private val _placesLiveData = MutableLiveData<PlacesResource>().apply {
        postValue(PlacesResource.Idle)
    }
    val placesLiveData: LiveData<PlacesResource> = _placesLiveData

    fun setDeviceLocation(searchLocation: SearchLocation) {
        _deviceLocationLiveData.postValue(searchLocation)
    }

    fun setSelectedLocation(searchLocation: SearchLocation) {
        viewModelScope.launch {
            _placesLiveData.postValue(PlacesResource.Loading)

            val data = placesRepository.findPlaces(searchLocation)
            _placesLiveData.postValue(PlacesResource.Success(data))
        }
    }

    fun setSelectedPlace(place: Place) {
        _selectedPlaceLiveData.postValue(place)
    }

    fun clearSelectedPlace(){
        _selectedPlaceLiveData.postValue(null)
    }

    sealed class PlacesResource {
        object Idle : PlacesResource()
        object Loading : PlacesResource()
        class Success(val places: List<Place>) : PlacesResource()
    }

}