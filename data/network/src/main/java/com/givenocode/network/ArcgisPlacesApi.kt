package com.givenocode.network

import com.esri.arcgisruntime.concurrent.ListenableFuture
import com.esri.arcgisruntime.geometry.Point
import com.esri.arcgisruntime.tasks.geocode.GeocodeParameters
import com.esri.arcgisruntime.tasks.geocode.GeocodeResult
import com.esri.arcgisruntime.tasks.geocode.LocatorTask
import com.givenocode.getupsidetest.domain.PlacesApi
import com.givenocode.getupsidetest.domain.model.SearchLocation
import com.givenocode.getupsidetest.domain.model.Place
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.*


class ArcgisPlacesApi: PlacesApi {

    private val locator =
        LocatorTask("https://geocode.arcgis.com/arcgis/rest/services/World/GeocodeServer")


    override suspend fun findPlaces(searchLocation: SearchLocation, maxResults: Int): List<Place> = withContext(IO) {
        val parameters = GeocodeParameters()

        parameters.maxResults = maxResults
        parameters.preferredSearchLocation = Point(searchLocation.latitude, searchLocation.longitude)
        parameters.outputLanguageCode = Locale.getDefault().language
        parameters.categories.add("Food")

        parameters.resultAttributeNames.add("Place_addr")
        parameters.resultAttributeNames.add("Phone")

        // Execute the search and add the places to the graphics overlay.
        // Execute the search and add the places to the graphics overlay.
        val results: ListenableFuture<List<GeocodeResult>> =
            locator.geocodeAsync("", parameters)

        try {
            results.get().map {
                Place(
                    label = it.label,
                    address = it.attributes["Place_addr"]?.toString() ?: "",
                    phone = it.attributes["Phone"]?.toString() ?: "",
                    latitude = it.displayLocation.x,
                    longitude = it.displayLocation.y,

                )
            }
        } catch (e: Exception) {
            // todo handle exception
            emptyList()
        }
    }
}