package com.example.makro123.noteapp

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_note.*

class AddNote : AppCompatActivity() {

    val dbTable = "Notes"
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        try {
            val bundle: Bundle = intent.extras
            id = bundle.getInt("ID", 0)

            if (id != 0) {
                edTitle.setText(bundle.getString("Name"))
                etDes.setText(bundle.getString("Des"))
            }
        } catch (ex: Exception) {}

    }

    fun buAdd(view: View) {

        val dbManager = DbManager(this)
        val values = ContentValues()
        values.put("Title", edTitle.text.toString())
        values.put("Description", etDes.text.toString())

        if (id == 0) { // Called if needed to add a note
            val ID = dbManager.insert(values)
            if (ID > 0)
                Toast.makeText(this, "Note is added", Toast.LENGTH_LONG).show()
            else
                Toast.makeText(this, "Cannot add note", Toast.LENGTH_LONG).show()
        } else { // Called if needed to update a note
            val selectionArgs = arrayOf(id.toString())
            val ID = dbManager.update(values, "ID = ?", selectionArgs)

            if (ID > 0)
                Toast.makeText(this, "Note is updated", Toast.LENGTH_LONG).show()
            else
                Toast.makeText(this, "Cannot update note", Toast.LENGTH_LONG).show()
        }

        finish() // finish() called to STOP the current activity
    }

}
