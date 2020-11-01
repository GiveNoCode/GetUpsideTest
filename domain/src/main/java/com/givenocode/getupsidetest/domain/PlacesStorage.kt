package com.givenocode.getupsidetest.domain

import com.givenocode.getupsidetest.domain.model.Coordinates
import com.givenocode.getupsidetest.domain.model.Place

interface PlacesStorage {

    suspend fun savePlaces(places: List<Place>)

    suspend fun getPlaces(): List<Place>?

    suspend fun saveInitialCoordinates(coordinates: Coordinates)

    suspend fun getInitialCoordinates(): Coordinates?

}