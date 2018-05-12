package com.example.makro123.zooapp

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.animal_ticket.view.*

class MainActivity : AppCompatActivity() {

    var listOfAnimals = ArrayList<Animal>()
    var adapter: AnimalsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load Animals
        listOfAnimals.add(Animal("Baboon", "Monkey in trees and stuff", R.drawable.baboon, false))
        listOfAnimals.add(Animal("Bulldog", "Dog in trees and stuff", R.drawable.bulldog, false))
        listOfAnimals.add(Animal("Panda", "Bear in trees and stuff", R.drawable.panda, true))
        listOfAnimals.add(Animal("Bird", "Bird flies in trees and stuff", R.drawable.swallow_bird, false))
        listOfAnimals.add(Animal("Roki", "Teodorina mala maca", R.drawable.white_tiger, true))
        listOfAnimals.add(Animal("Zebra", "Black Horse in trees and stuff", R.drawable.zebra, false))

        adapter = AnimalsAdapter(this, listOfAnimals)
        lvAnimals.adapter = adapter

    }

    fun delete(index: Int) {
        listOfAnimals.removeAt(index)
        adapter!!.notifyDataSetChanged()
    }

    fun add(index: Int) {
        listOfAnimals.add(index, listOfAnimals[index])
        adapter!!.notifyDataSetChanged()
    }

    inner class AnimalsAdapter: BaseAdapter {

        var listOfAnimals = ArrayList<Animal>()
        var context: Context? = null

        constructor(context: Context, listOfAnimals: ArrayList<Animal>): super() {
            this.listOfAnimals = listOfAnimals
            this.context = context
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val animal = listOfAnimals[position]

            if (animal.isKiller == true) {

                val inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val myView = inflator.inflate(R.layout.animal_killer_ticket, null) // Get Animal Ticket XML file

                myView.txtName.text = animal.name
                myView.txtDes.text = animal.des
                myView.imgName.setImageResource(animal.image!!)

                // Check if image was clicked
                myView.imgName.setOnClickListener {

//                    add(position)
//                    delete(position)

                    val intent = Intent(context, AnimalInfo:: class.java) // AnimalInfo = class from AnimalInfo File
                    intent.putExtra("name", animal.name!!)
                    intent.putExtra("des", animal.des!!)
                    intent.putExtra("image", animal.image!!)
                    context!!.startActivity(intent)
                }

                return myView

            } else {
                val inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val myView = inflator.inflate(R.layout.animal_ticket, null) // Get Animal Ticket XML file

                myView.txtName.text = animal.name
                myView.txtDes.text = animal.des
                myView.imgName.setImageResource(animal.image!!)

                // Check if image was clicked
                myView.imgName.setOnClickListener {

//                    add(position)
//                    delete(position)

                    val intent = Intent(context, AnimalInfo:: class.java) // AnimalInfo = class from AnimalInfo File
                    intent.putExtra("name", animal.name!!)
                    intent.putExtra("des", animal.des!!)
                    intent.putExtra("image", animal.image!!)
                    context!!.startActivity(intent)
                }

                return myView
            }
        }

        override fun getItem(position: Int): Any {
            return listOfAnimals[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return listOfAnimals.size
        }

    }

}
