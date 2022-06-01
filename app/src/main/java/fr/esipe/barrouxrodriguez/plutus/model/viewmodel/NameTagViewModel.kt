package fr.esipe.barrouxrodriguez.plutus.model.viewmodel

import android.app.Application
import androidx.lifecycle.*
import fr.esipe.barrouxrodriguez.plutus.model.NoteBookDatabase
import fr.esipe.barrouxrodriguez.plutus.model.dao.NameTagDao
import fr.esipe.barrouxrodriguez.plutus.model.entity.NameTag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NameTagViewModel(application: Application) : AndroidViewModel(application) {
    val readAllData: LiveData<List<NameTag>>
    val readAllPredefined: LiveData<List<NameTag>>
    val predefTags: List<String> = listOf("-food", "-car", "+wage", "+interest", "=standard")
    private var nameTagDao: NameTagDao = NoteBookDatabase.getInstance(application).NameTagDao()

    init {
        readAllData = nameTagDao.getAll()
        readAllPredefined = nameTagDao.getAllPredefined()
    }

    fun insertAll(vararg nameTag: NameTag) {
        viewModelScope.launch(Dispatchers.IO) {
            nameTagDao.insertAll(*nameTag)
        }
    }

    fun delete(nameTag: NameTag) {
        viewModelScope.launch(Dispatchers.IO) {
            nameTagDao.delete(nameTag)
        }
    }

    fun deleteByTransaction(idTransaction: Int){
        viewModelScope.launch(Dispatchers.IO) {
            nameTagDao.deleteByTransaction(idTransaction)
        }
    }


    class NameTagModelFactory(
        private val application: Application,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            if (modelClass.isAssignableFrom(NameTagViewModel::class.java)) {
                return NameTagViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}