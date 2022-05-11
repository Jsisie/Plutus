package fr.esipe.barrouxrodriguez.plutus.model

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PlutusDataBaseHelper(
    context: Context,
) : SQLiteOpenHelper(context, DATABASE_NAME, null, CURRENT_VERSION) {

    companion object {
        const val DATABASE_NAME = "plutus"
        const val CURRENT_VERSION = 1

        const val NOTEBOOK_TABLE = """CREATE TABLE NoteBook (title)"""
        const val TRANSACTION_TABLE = """"""
        const val BUDGET_TABLE = """"""
        const val NAMETAG_TABLE = """"""
    }

    /**
     * Execute all the queries to create the table in the database
     */
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(NOTEBOOK_TABLE)
        db.execSQL(TRANSACTION_TABLE)
        db.execSQL(BUDGET_TABLE)
        db.execSQL(NAMETAG_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // to be implemented if the schema changes from oldVersion to newVersion
        // we use ALTER TABLE SQL statements to transform the tables
    }
}