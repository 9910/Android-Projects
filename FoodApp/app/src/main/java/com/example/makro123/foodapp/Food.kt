package com.example.makro123.foodapp

class Food {
    var name: String? = null
    var des: String? = null
    var image: Int? = null

    constructor(name: String, des: String, image: Int) {
        this.name = name
        this.des = des
        this.image = image
    }
}