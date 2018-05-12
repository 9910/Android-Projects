package com.example.makro123.zooapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_animal_info.*

class AnimalInfo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal_info)

        // Get all extras from other activity
        val bundle: Bundle = intent.extras
        val name = bundle.getString("name")
        val des = bundle.getString("des")
        val img = bundle.getInt("image")

        // Set data to current activity
        imgAnimal.setImageResource(img)
        txtName.text = name
        txtDes.text = des

    }
}
