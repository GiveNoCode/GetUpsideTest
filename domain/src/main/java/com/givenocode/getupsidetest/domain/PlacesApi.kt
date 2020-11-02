package com.givenocode.getupsidetest.domain

import com.givenocode.getupsidetest.domain.model.SearchLocation
import com.givenocode.getupsidetest.domain.model.Place


interface PlacesApi {

    suspend fun findPlaces(searchLocation: SearchLocation, maxResults: Int): List<Place>
}