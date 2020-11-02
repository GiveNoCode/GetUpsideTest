package com.givenocode.getupsidetest

import android.app.Application
import com.givenocode.getupsidetest.storage.RealmPlacesStorage

class App : Application() {

    companion object {
        val realmPlaceStorage = RealmPlacesStorage()
    }

    override fun onCreate() {
        super.onCreate()

        realmPlaceStorage.initRealm(this)
    }

}