package com.givenocode.getupsidetest.storage.model

import io.realm.RealmObject

internal open class RealmPlace : RealmObject() {

    var label = ""

    var address: String = ""

    var phone: String = ""

    var latitude = 0.0

    var longitude = 0.0
}