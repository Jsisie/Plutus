package fr.esipe.barrouxrodriguez.plutus.model

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NoteBookDatabaseHelper(
    context: Context,
) : SQLiteOpenHelper(context, DATABASE_NAME, null, CURRENT_VERSION) {

    companion object {
        const val DATABASE_NAME = "notepad"
        const val CURRENT_VERSION = 1

        const val NOTE_VERSION_TABLE = """
            CREATE TABLE NoteVersion ("")
            
            
            """
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(NOTE_VERSION_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // to be implemented if the schema changes from oldVersion to newVersion
        // we use ALTER TABLE SQL statements to transform the tables
    }
}