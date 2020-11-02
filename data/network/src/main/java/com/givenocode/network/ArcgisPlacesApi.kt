package com.givenocode.network

import com.esri.arcgisruntime.concurrent.ListenableFuture
import com.esri.arcgisruntime.geometry.Point
import com.esri.arcgisruntime.tasks.geocode.GeocodeParameters
import com.esri.arcgisruntime.tasks.geocode.GeocodeResult
import com.esri.arcgisruntime.tasks.geocode.LocatorTask
import com.givenocode.getupsidetest.domain.PlacesApi
import com.givenocode.getupsidetest.domain.model.Coordinates
import com.givenocode.getupsidetest.domain.model.Place
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext


class ArcgisPlacesApi: PlacesApi {

    private val locator =
        LocatorTask("https://geocode.arcgis.com/arcgis/rest/services/World/GeocodeServer")


    override suspend fun findPlaces(coordinates: Coordinates, maxResults: Int): List<Place> = withContext(IO) {
        val parameters = GeocodeParameters()

        parameters.preferredSearchLocation = Point(coordinates.latitude, coordinates.longitude)
        parameters.maxResults = maxResults

        val outputAttributes = parameters.resultAttributeNames
        outputAttributes.add("*")

        // Execute the search and add the places to the graphics overlay.
        // Execute the search and add the places to the graphics overlay.
        val results: ListenableFuture<List<GeocodeResult>> =
            locator.geocodeAsync("Food", parameters)

        results.get().map {
            Place(
                label = it.label,
                latitude =  it.displayLocation.x,
                longitude = it.displayLocation.y)
        }
    }
}