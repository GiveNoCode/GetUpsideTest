package com.givenocode.getupsidetest.storage.model

import io.realm.RealmObject

internal open class RealmSearchLocation  : RealmObject() {

    var latitude = 0.0
    var longitude = 0.0
}