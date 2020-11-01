package com.givenocode.getupsidetest.storage

import com.givenocode.getupsidetest.domain.model.Coordinates
import com.givenocode.getupsidetest.domain.model.Place

class InMemoryStorage : com.givenocode.getupsidetest.domain.PlacesStorage {

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