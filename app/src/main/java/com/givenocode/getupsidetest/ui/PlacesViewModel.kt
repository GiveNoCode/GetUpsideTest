package com.givenocode.getupsidetest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.givenocode.getupsidetest.data.Coordinates

class PlacesViewModel : ViewModel() {

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
        if (_placesLiveData.value == PlacesResource.Idle)
            _placesLiveData.postValue(PlacesResource.Loading)
        else
            _placesLiveData.postValue(PlacesResource.Idle)
    }

    sealed class PlacesResource {
        object Idle : PlacesResource()
        object Loading : PlacesResource()
    }

}