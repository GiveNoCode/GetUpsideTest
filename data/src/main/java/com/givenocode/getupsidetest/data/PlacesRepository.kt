package com.givenocode.getupsidetest.data

import com.givenocode.getupsidetest.data.arcgis.ArcgisPlacesApi
import com.givenocode.getupsidetest.data.database.PlacesStorage
import com.givenocode.getupsidetest.data.model.Coordinates
import com.givenocode.getupsidetest.data.model.Place
import kotlin.math.abs

class PlacesRepository constructor(
    private val placesApi: ArcgisPlacesApi,
    private val placesStorage: PlacesStorage
) {

    companion object {
        private const val MAX_RESULTS = 20
        private const val LOCATION_TRESHOLD = 0.1
    }

    suspend fun findPlaces(coordinates: Coordinates): List<Place> {

        val savedCoordinates = placesStorage.getInitialCoordinates()
        val savedPlaces = placesStorage.getPlaces()
        if (savedCoordinates != null
            && savedPlaces != null
            && abs(savedCoordinates.latitude - coordinates.latitude) < LOCATION_TRESHOLD
            && abs(savedCoordinates.latitude - coordinates.latitude) < LOCATION_TRESHOLD
        ) {
            return savedPlaces
        }

        val results = placesApi.findPlaces(coordinates, MAX_RESULTS)

        placesStorage.saveInitialCoordinates(coordinates)
        placesStorage.savePlaces(results)

        return results
    }


}