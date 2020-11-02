package com.givenocode.getupsidetest.storage.mapper

import com.givenocode.getupsidetest.domain.model.Coordinates
import com.givenocode.getupsidetest.storage.model.RealmCoordinates

internal fun Coordinates.toRealmObject() : RealmCoordinates {
    return RealmCoordinates().also {
        it.latitude = latitude
        it.longitude = longitude
    }
}

internal fun RealmCoordinates.toDomainObject(): Coordinates {
    return Coordinates(latitude, longitude)
}