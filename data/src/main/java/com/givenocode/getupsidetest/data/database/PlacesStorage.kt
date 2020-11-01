package com.givenocode.getupsidetest.data.database

import com.givenocode.getupsidetest.data.model.Coordinates
import com.givenocode.getupsidetest.data.model.Place

interface PlacesStorage {

    suspend fun savePlaces(places: List<Place>)

    suspend fun getPlaces(): List<Place>?

    suspend fun saveInitialCoordinates(coordinates: Coordinates)

    suspend fun getInitialCoordinates(): Coordinates?

}