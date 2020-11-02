package com.givenocode.getupsidetest.domain

import com.givenocode.getupsidetest.domain.model.SearchLocation
import com.givenocode.getupsidetest.domain.model.Place

interface PlacesStorage {

    suspend fun savePlaces(searchLocation: SearchLocation, places: List<Place>)

    suspend fun getPlaces(): List<Place>?

    suspend fun getSearchLocation(): SearchLocation?

}