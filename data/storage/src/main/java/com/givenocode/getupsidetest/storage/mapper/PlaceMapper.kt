package com.givenocode.getupsidetest.storage.mapper

import com.givenocode.getupsidetest.domain.model.Place
import com.givenocode.getupsidetest.storage.model.RealmPlace

internal fun Place.toRealmObject(): RealmPlace {
    return RealmPlace().also {
        it.label = label
        it.address = address
        it.phone = phone
        it.latitude = latitude
        it.longitude = longitude
    }
}

internal fun RealmPlace.toDomainObject(): Place {
    return Place(
        label = label,
        address = address,
        phone = phone,
        latitude = latitude,
        longitude = longitude
    )
}