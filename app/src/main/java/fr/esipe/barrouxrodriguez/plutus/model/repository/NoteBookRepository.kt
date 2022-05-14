package fr.esipe.barrouxrodriguez.plutus.model.repository

import androidx.lifecycle.LiveData
import fr.esipe.barrouxrodriguez.plutus.model.dao.NoteBookDao
import fr.esipe.barrouxrodriguez.plutus.model.entity.NoteBook
import fr.esipe.barrouxrodriguez.plutus.model.entity.NoteBookWithTransactionsAndBudget

class NoteBookRepository(private val noteBookDao: NoteBookDao) {

    val readAllData: LiveData<List<NoteBook>> = noteBookDao.getAll()

    fun loadNoteBookWithTransactionsAndBudgetById(noteBook: Int): LiveData<NoteBookWithTransactionsAndBudget> =
        noteBookDao.loadNoteBookWithTransactionsAndBudgetById(noteBook)

    suspend fun insertAll(vararg noteBook: NoteBook) {
        noteBookDao.insertAll(*noteBook)
    }


    suspend fun delete(noteBook: NoteBook) {
        noteBookDao.delete(noteBook)
    }
}
