package com.givenocode.getupsidetest.domain

import com.givenocode.getupsidetest.domain.model.Coordinates
import com.givenocode.getupsidetest.domain.model.Place


interface PlacesApi {

    suspend fun findPlaces(coordinates: Coordinates, maxResults: Int): List<Place>
}