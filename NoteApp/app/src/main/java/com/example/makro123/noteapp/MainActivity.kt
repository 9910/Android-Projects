package com.example.makro123.noteapp

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.BaseAdapter
import android.widget.SearchView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.ticket.view.*

class MainActivity : AppCompatActivity() {

    private var listOfNotes = ArrayList<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Add test Data
//        listOfNotes.add(Note(1, "Breakfast", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic"))
//        listOfNotes.add(Note(2, "Lunch", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic"))
//        listOfNotes.add(Note(3, "Diner", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic"))

        // Setup Adapter
        // Load from Database
        loadQuery("%")

    }

    override fun onResume() {
        super.onResume()
        loadQuery("%")
    }

    fun loadQuery(title: String) {

        val dbManager = DbManager(this)
        val projection = arrayOf("ID", "Title", "Description")
        val selectionArgs = arrayOf(title)              // ? = title
        val cursor = dbManager.query(projection, "Title like ?", selectionArgs, "Title")
        listOfNotes.clear()

        if (cursor.moveToFirst()) {
            do {
                val ID = cursor.getInt(cursor.getColumnIndex("ID"))
                val title = cursor.getString(cursor.getColumnIndex("Title"))
                val description = cursor.getString(cursor.getColumnIndex("Description"))

                listOfNotes.add(Note(ID, title, description))

            } while (cursor.moveToNext())
        }

        val notesAdapter = myNotesAdaper(listOfNotes, this)
        lvNotes.adapter = notesAdapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        // Put myMenu in Main Activity Menu
        menuInflater.inflate(R.menu.main_menu, menu)

        // Define listeners for Query Changes and Submit
        val searchV = menu!!.findItem(R.id.app_bar_search).actionView as SearchView
        val searchM = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchV.setSearchableInfo(searchM.getSearchableInfo(componentName)) // Search in this activity
        searchV.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(applicationContext, query, Toast.LENGTH_LONG).show()
                // Search the Database
                loadQuery("%$query%")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item!!.itemId) {
            R.id.addNote -> {
                // Go to AddPage Activity
                val intent = Intent(this, AddNote::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    inner class myNotesAdaper: BaseAdapter {

        private var listOfNotes = ArrayList<Note>()
        var context: Context? = null

        constructor(listOfNotes: ArrayList<Note>, context: Context): super() {
            this.listOfNotes = listOfNotes
            this.context = context
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            // Read "Ticket" Layout
            val myView = layoutInflater.inflate(R.layout.ticket, null)
            val note = this.listOfNotes[position]

            // Input Data
            myView.tvTitle.text = note.name
            myView.tvDes.text = note.des
            myView.ivDelete.setOnClickListener {

                val dbManager = DbManager(this.context!!)
                val selectionArgs = arrayOf(note.noteID.toString())
                dbManager.delete("ID = ?", selectionArgs)

                loadQuery("%")
            }
            myView.ivEdit.setOnClickListener {
                callAddNoteActivity(note)
            }

            return myView
        }

        override fun getItem(position: Int): Any {
            return this.listOfNotes[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return this.listOfNotes.size
        }

    }

    fun callAddNoteActivity(note: Note) {

        val intent = Intent(this, AddNote::class.java)
        intent.putExtra("ID", note.noteID)
        intent.putExtra("Name", note.name)
        intent.putExtra("Des", note.des)

        startActivity(intent)
    }

}
