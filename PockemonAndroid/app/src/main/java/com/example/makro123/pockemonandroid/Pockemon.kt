package com.example.makro123.pockemonandroid

import android.location.Location

class Pockemon {

    var name: String? = null
    var description: String? = null
    var image: Int? = null
    var power: Double? = null
    var isCatch: Boolean = false
    var location: Location? = null

    constructor (image: Int, name: String, description: String, power: Double, lat: Double, log: Double) {
        this.name = name
        this.description = description
        this.image = image
        this.power = power
        this.location = Location(name)
        this.location!!.latitude = lat
        this.location!!.longitude = log
    }

}