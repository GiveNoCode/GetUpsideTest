package com.givenocode.getupsidetest.data

import com.givenocode.getupsidetest.data.arcgis.ArcgisPlacesApi
import com.givenocode.getupsidetest.data.model.Place

class PlacesRepository constructor(
   private val placesApi: ArcgisPlacesApi
) {

    companion object {
        private const val MAX_RESULTS = 20
    }

    suspend fun findPlaces(latitude: Double, longitude: Double): List<Place> = placesApi.findPlaces(latitude, longitude, MAX_RESULTS)
}