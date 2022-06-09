package fr.esipe.barrouxrodriguez.plutus.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fr.esipe.barrouxrodriguez.plutus.model.dao.*
import fr.esipe.barrouxrodriguez.plutus.model.entity.*
import fr.esipe.barrouxrodriguez.plutus.utils.Converters

@Database(
    version = 1,
    entities = [NoteBook::class, NameTag::class, Budget::class, Transaction::class, Filter::class]
)

@TypeConverters(Converters::class)
abstract class NoteBookDatabase : RoomDatabase() {
    abstract fun BudgetDao(): BudgetDao
    abstract fun NameTagDao(): NameTagDao
    abstract fun NoteBookDao(): NoteBookDao
    abstract fun TransactionDao(): TransactionDao
    abstract fun FilterDao(): FilterDao

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: NoteBookDatabase? = null

        fun getInstance(context: Context): NoteBookDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): NoteBookDatabase {

            return Room.databaseBuilder(
                context.applicationContext,
                NoteBookDatabase::class.java, "PlutusDB-61"
            ).build()
        }
    }
}