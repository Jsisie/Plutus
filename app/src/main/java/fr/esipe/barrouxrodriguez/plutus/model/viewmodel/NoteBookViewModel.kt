package fr.esipe.barrouxrodriguez.plutus.model.viewmodel

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.*
import fr.esipe.barrouxrodriguez.plutus.model.NoteBookDatabase
import fr.esipe.barrouxrodriguez.plutus.model.dao.NoteBookDao
import fr.esipe.barrouxrodriguez.plutus.model.entity.NoteBook
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteBookViewModel(application: Application) : AndroidViewModel(application) {
    val readAllData: LiveData<List<NoteBook>>
    private var noteBookDao: NoteBookDao = NoteBookDatabase.getInstance(application).NoteBookDao()

    private fun duplicationHandler() {
        // TODO: text translation
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(
                getApplication<Application>(),
                "Notebook already existing, find an another name",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    init {
        readAllData = noteBookDao.getAll()
    }

    @Throws(SQLiteConstraintException::class)
    fun insertAll(vararg noteBook: NoteBook) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                noteBookDao.insertAll(*noteBook)
            } catch (e: SQLiteConstraintException) {
                duplicationHandler()
            }
        }
    }

    @Throws(SQLiteConstraintException::class)
    fun update(noteBook: NoteBook) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                noteBookDao.update(noteBook)
            } catch (e: SQLiteConstraintException) {
                duplicationHandler()
            }
        }
    }

    fun delete(noteBook: NoteBook) {
        viewModelScope.launch(Dispatchers.IO) {
            noteBookDao.delete(noteBook)
        }
    }

    class NoteBookModelFactory(
        private val application: Application,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            if (modelClass.isAssignableFrom(NoteBookViewModel::class.java)) {
                return NoteBookViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}