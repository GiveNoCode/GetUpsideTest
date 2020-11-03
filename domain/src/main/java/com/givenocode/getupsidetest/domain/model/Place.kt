package com.givenocode.getupsidetest.domain.model

data class Place(
    val label: String,
    val address: String,
    val phone: String,
    val latitude: Double,
    val longitude: Double
)