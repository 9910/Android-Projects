package com.example.makro123.foodapp

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.food_ticket.view.*

class MainActivity : AppCompatActivity() {

    var listOfFood = ArrayList<Food>()
    var adapter: FoodAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load Food
        listOfFood.add(Food("Coffee", "Hot coffee drink and stuff", R.drawable.coffee_pot))
        listOfFood.add(Food("Espresso", "Hot espresso drink and stuff", R.drawable.espresso))
        listOfFood.add(Food("Fries", "Hot french fries drink and stuff", R.drawable.french_fries))
        listOfFood.add(Food("Honey", "Hot honey drink and stuff", R.drawable.honey))
        listOfFood.add(Food("Strawberry Ice Cream","Hot strawberry ice cream drink and stuff", R.drawable.strawberry_ice_cream))
        listOfFood.add(Food("Sugar Cubes", "Hot sugar cubes drink and stuff", R.drawable.sugar_cubes))

        // Load Adapter
        adapter = FoodAdapter(listOfFood, this)

        // Set adapter to the corresponding View
        gvListFood.adapter = adapter

    }

    class FoodAdapter: BaseAdapter {

        var listOfFood = ArrayList<Food>()
        var context: Context? = null

        constructor(listOfFood: ArrayList<Food>, context: Context): super() {
            this.context = context
            this.listOfFood = listOfFood
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            val food = listOfFood[position]
            // Load Layout with context service
            val inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val foodView = inflator.inflate(R.layout.food_ticket, null)

            // Change values in layout
            foodView.imgFood.setImageResource(food.image!!)
            foodView.txtFood.text = food.name!!
            foodView.setOnClickListener {

                val intent = Intent(context, FoodDetails::class.java)
                intent.putExtra("name", food.name)
                intent.putExtra("des", food.des)
                intent.putExtra("image", food.image!!)

                // Call new Activity
                context!!.startActivity(intent)
            }

            return foodView
        }

        override fun getItem(position: Int): Any {
            return listOfFood[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return listOfFood.size
        }

    }

}
