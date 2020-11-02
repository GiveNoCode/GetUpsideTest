package com.givenocode.getupsidetest.domain

import com.givenocode.getupsidetest.domain.model.SearchLocation
import com.givenocode.getupsidetest.domain.model.Place
import kotlin.math.abs

class PlacesRepository constructor(
    private val placesApi: PlacesApi,
    private val placesStorage: PlacesStorage
) {

    companion object {
        private const val MAX_RESULTS = 20
        private const val LOCATION_TRESHOLD = 0.1
    }

    suspend fun findPlaces(searchLocation: SearchLocation): List<Place> {

        val savedCoordinates = placesStorage.getSearchLocation()
        val savedPlaces = placesStorage.getPlaces()
        if (savedCoordinates != null
            && savedPlaces != null
            && abs(savedCoordinates.latitude - searchLocation.latitude) < LOCATION_TRESHOLD
            && abs(savedCoordinates.latitude - searchLocation.latitude) < LOCATION_TRESHOLD
        ) {
            return savedPlaces
        }

        val results = placesApi.findPlaces(searchLocation, MAX_RESULTS)

        placesStorage.savePlaces(searchLocation, results)

        return results
    }


}