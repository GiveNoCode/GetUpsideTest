package com.givenocode.getupsidetest.storage

import android.content.Context
import android.util.Log
import com.givenocode.getupsidetest.domain.PlacesStorage
import com.givenocode.getupsidetest.domain.model.Coordinates
import com.givenocode.getupsidetest.domain.model.Place
import com.givenocode.getupsidetest.storage.mapper.toRealmObject
import com.givenocode.getupsidetest.storage.mapper.toDomainObject
import com.givenocode.getupsidetest.storage.model.RealmCoordinates
import com.givenocode.getupsidetest.storage.model.RealmPlace
import io.realm.Realm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RealmPlacesStorage : PlacesStorage {

    fun initRealm(context: Context)  {
        Realm.init(context)
    }

    private val realm get() = Realm.getDefaultInstance()

    override suspend fun savePlaces(places: List<Place>) = withContext(Dispatchers.IO){
        executeTransaction {
            where(RealmPlace::class.java).findAll().deleteAllFromRealm()
            copyToRealm(places.map { it.toRealmObject() })
        }
    }

    override suspend fun getPlaces(): List<Place>? = withContext(Dispatchers.IO){
        realm.where(RealmPlace::class.java).findAll().map { it.toDomainObject() }
    }

    override suspend fun saveInitialCoordinates(coordinates: Coordinates)= withContext(Dispatchers.IO) {
        executeTransaction {
            where(RealmCoordinates::class.java).findAll().deleteAllFromRealm()
            copyToRealm(coordinates.toRealmObject())
        }
    }

    override suspend fun getInitialCoordinates(): Coordinates? = withContext(Dispatchers.IO){
        realm.where(RealmCoordinates::class.java).findAll().firstOrNull()?.let {
            it.toDomainObject()
        }
    }

    private fun executeTransaction(action: Realm.() -> Unit) {
        realm.apply {
            beginTransaction()
            action()
            commitTransaction()
        }
    }
}