package fr.esipe.barrouxrodriguez.plutus.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = [NoteBook::class, NameTag::class, Budget::class, Transaction::class])
abstract class NoteBookDatabase: RoomDatabase() {
}