package com.givenocode.getupsidetest.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlacesViewModel : ViewModel() {

    private val _displayModeLiveData = MutableLiveData<DisplayMode>().apply {
        postValue(DisplayMode.Map)
    }
    val displayModeLiveData: LiveData<DisplayMode> = _displayModeLiveData

    fun setDisplayMode(displayMode: DisplayMode){
        _displayModeLiveData.postValue(displayMode)
    }
}