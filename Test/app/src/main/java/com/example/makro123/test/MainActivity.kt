package com.example.makro123.test

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        buFindAge.setOnClickListener {
//
//
//        }
    }

    fun buFindAgeEvent(view: View) {
        // Run when button clicked
        val yearOfBirth:Int = txtYearOfBirth.text.toString().toInt()
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val myAge = currentYear - yearOfBirth
        txtShowAge.text = myAge.toString()
    }

}
