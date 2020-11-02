package com.givenocode.getupsidetest.storage.mapper

import com.givenocode.getupsidetest.domain.model.SearchLocation
import com.givenocode.getupsidetest.storage.model.RealmSearchLocation

internal fun SearchLocation.toRealmObject() : RealmSearchLocation {
    return RealmSearchLocation().also {
        it.latitude = latitude
        it.longitude = longitude
    }
}

internal fun RealmSearchLocation.toDomainObject(): SearchLocation {
    return SearchLocation(latitude, longitude)
}