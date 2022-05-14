package fr.esipe.barrouxrodriguez.plutus.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import fr.esipe.barrouxrodriguez.plutus.model.entity.NoteBook
import fr.esipe.barrouxrodriguez.plutus.model.entity.NoteBookWithTransactionsAndBudget

@Dao
interface NoteBookDao {
    @Query("SELECT * FROM NoteBook")
    fun getAll(): LiveData<List<NoteBook>>

    @Transaction
    @Query("SELECT * FROM NoteBook WHERE idNotebook = (:noteBook)")
    fun loadNoteBookWithTransactionsAndBudgetById(noteBook: Int): LiveData<NoteBookWithTransactionsAndBudget>

    @Insert
    suspend fun insertAll(vararg noteBook: NoteBook)

    @Delete
    suspend fun delete(noteBook: NoteBook)
}
