package com.example.makro123.foodapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_food_details.*

class FoodDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_details)

        val bundle = intent.extras
        imgFoodDetail.setImageResource(bundle.getInt("image"))
        txtName.text = bundle.getString("name")
        txtDes.text = bundle.getString("des")
    }
}
