package com.example.makro123.noteapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQuery
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast

class DbManager {

    val dbName: String = "MyNotes"
    val dbTable: String = "Notes"
    val colID: String = "ID"
    val colTitle: String = "Title"
    val colDes: String = "Description"
    val dbVersion: Int = 1
    val sqlCreateTable = "CREATE TABLE IF NOT EXISTS " + dbTable + " ("+ colID +" INTEGER PRIMARY KEY," +
            colTitle + " TEXT, " + colDes + " TEXT);"
    var sqlDB: SQLiteDatabase? = null

    constructor(context: Context) {
        val db = DatabaseHelperNotes(context)
        sqlDB = db.writableDatabase
    }

    // Used for additional database operations(onCreate, onUpgrade)
    inner class DatabaseHelperNotes: SQLiteOpenHelper {

        var context: Context? = null

        constructor(context: Context): super(context, dbName, null, dbVersion) {
            this.context = context
        }

        override fun onCreate(db: SQLiteDatabase?) {
            // Create DB if doesn't exist already
            db!!.execSQL(sqlCreateTable)
            Toast.makeText(this.context, "Database is created", Toast.LENGTH_LONG).show()
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS" + dbTable)
        }

    }

    fun insert(values: ContentValues): Long {

        val ID = sqlDB!!.insert(dbTable, "", values)
        return ID
    }
    //        Columns
    fun query(projection: Array<String>, selection: String, selectionArgs: Array<String>, sortOrder: String): Cursor {

        val qb = SQLiteQueryBuilder()
        qb.tables = dbTable
        val cursor = qb.query(sqlDB, projection, selection, selectionArgs, null, null, sortOrder)

        return cursor
    }

    fun delete(selection: String, selectionArgs: Array<String>): Int {
        val count = sqlDB!!.delete(dbTable, selection, selectionArgs)
        return count
    }

    fun update(values: ContentValues, selection: String, selectionArgs: Array<String>): Int {
        val count = sqlDB!!.update(dbTable, values, selection, selectionArgs)
        return count
    }

}