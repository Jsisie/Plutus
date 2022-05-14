package fr.esipe.barrouxrodriguez.plutus.model.repository

import android.app.Application
import androidx.lifecycle.*
import fr.esipe.barrouxrodriguez.plutus.model.dao.NoteBookDao
import fr.esipe.barrouxrodriguez.plutus.model.entity.NoteBook
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteBookViewModel(application: Application) : AndroidViewModel(application)  {
    private val readAllData: LiveData<List<NoteBook>>
    private var noteBookDao: NoteBookDao = NoteBookDatabase.getInstance(application).NoteBookDao()

    init {
        readAllData =  noteBookDao.getAll()
    }

    fun insertAll(vararg noteBook: NoteBook) {
        viewModelScope.launch(Dispatchers.IO) {
            noteBookDao.insertAll(*noteBook)
        }
    }

    fun delete(noteBook: NoteBook) {
        viewModelScope.launch(Dispatchers.IO) {
            noteBookDao.delete(noteBook)
        }
    }

    class NoteBookModelFactory(
        private val application: Application
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