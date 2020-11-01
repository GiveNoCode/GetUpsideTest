package com.givenocode.getupsidetest.data.database

import com.givenocode.getupsidetest.data.model.Coordinates
import com.givenocode.getupsidetest.data.model.Place

class InMemoryStorage : PlacesStorage {

    private var places: List<Place>? = null
    private var initialCoordinates: Coordinates? = null

    override suspend fun savePlaces(places: List<Place>) {
        this.places = places
    }

    override suspend fun getPlaces() = places

    override suspend fun saveInitialCoordinates(coordinates: Coordinates) {
        initialCoordinates = coordinates
    }

    override suspend fun getInitialCoordinates() = initialCoordinates
}