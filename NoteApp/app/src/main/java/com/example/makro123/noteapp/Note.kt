package com.example.makro123.noteapp

class Note {

    var noteID: Int? = null
    var name: String? = null
    var des: String? = null

    constructor(noteID: Int, name: String, des: String) {
        this.noteID = noteID
        this.name = name
        this.des = des
    }

}