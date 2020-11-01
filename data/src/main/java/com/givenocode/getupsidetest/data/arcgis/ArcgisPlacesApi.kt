package com.givenocode.getupsidetest.data.arcgis

import com.esri.arcgisruntime.concurrent.ListenableFuture
import com.esri.arcgisruntime.geometry.Point
import com.esri.arcgisruntime.tasks.geocode.GeocodeParameters
import com.esri.arcgisruntime.tasks.geocode.GeocodeResult
import com.esri.arcgisruntime.tasks.geocode.LocatorTask
import com.givenocode.getupsidetest.data.model.Coordinates
import com.givenocode.getupsidetest.data.model.Place
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext


class ArcgisPlacesApi {

    private val locator =
        LocatorTask("https://geocode.arcgis.com/arcgis/rest/services/World/GeocodeServer")


    suspend fun findPlaces(latitude: Double, longitude: Double, maxResults: Int): List<Place> = withContext(IO) {
        val parameters = GeocodeParameters()

        parameters.preferredSearchLocation = Point(latitude, longitude)
        parameters.maxResults = maxResults

        val outputAttributes = parameters.resultAttributeNames
        outputAttributes.add("*")

        // Execute the search and add the places to the graphics overlay.
        // Execute the search and add the places to the graphics overlay.
        val results: ListenableFuture<List<GeocodeResult>> =
            locator.geocodeAsync("Food", parameters)

        @Suppress("BlockingMethodInNonBlockingContext")
        results.get().map {
            Place(
                label = it.label,
                coordinates = Coordinates(it.displayLocation.x, it.displayLocation.y))
        }
    }
}