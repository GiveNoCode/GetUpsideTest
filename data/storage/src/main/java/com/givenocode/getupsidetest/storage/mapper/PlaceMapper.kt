package com.givenocode.getupsidetest.storage.mapper

import com.givenocode.getupsidetest.domain.model.Place
import com.givenocode.getupsidetest.storage.model.RealmPlace

internal fun Place.toRealmObject(): RealmPlace {
    return RealmPlace().also {
        it.label = label
        it.latitude = latitude
        it.longitude = longitude
    }
}

internal fun RealmPlace.toDomainObject(): Place {
    return Place(label, latitude, longitude)
}