package fr.esipe.barrouxrodriguez.plutus.model

import android.app.Application
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fr.esipe.barrouxrodriguez.plutus.model.dao.BudgetDao
import fr.esipe.barrouxrodriguez.plutus.model.dao.NameTagDao
import fr.esipe.barrouxrodriguez.plutus.model.dao.NoteBookDao
import fr.esipe.barrouxrodriguez.plutus.model.dao.TransactionDao
import fr.esipe.barrouxrodriguez.plutus.model.entity.Budget
import fr.esipe.barrouxrodriguez.plutus.model.entity.NameTag
import fr.esipe.barrouxrodriguez.plutus.model.entity.NoteBook
import fr.esipe.barrouxrodriguez.plutus.model.entity.Transaction
import fr.esipe.barrouxrodriguez.plutus.model.viewmodel.NoteBookViewModel
import fr.esipe.barrouxrodriguez.plutus.notebookViewModel

@Database(
    version = 1,
    entities = [NoteBook::class, NameTag::class, Budget::class, Transaction::class]
)

@TypeConverters(Converters::class)
abstract class NoteBookDatabase : RoomDatabase() {
    abstract fun BudgetDao(): BudgetDao
    abstract fun NameTagDao(): NameTagDao
    abstract fun NoteBookDao(): NoteBookDao
    abstract fun TransactionDao(): TransactionDao

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
                NoteBookDatabase::class.java, "notebook-db32"
            ).build()
        }
    }
}