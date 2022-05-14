package fr.esipe.barrouxrodriguez.plutus.model

import android.app.Application
import androidx.lifecycle.*
import fr.esipe.barrouxrodriguez.plutus.model.entity.NameTag
import fr.esipe.barrouxrodriguez.plutus.model.repository.NameTagRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NameTagViewModel(application: Application) : AndroidViewModel(application) {
    val readAllData: LiveData<List<NameTag>>
    private val repository: NameTagRepository

    init {
        val nameTagDao = NoteBookDatabase.getInstance(application).NameTagDao()
        repository = NameTagRepository((nameTagDao))
        readAllData = repository.readAllData
    }

    fun insertAll(vararg nameTag: NameTag) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertAll(*nameTag)
        }
    }

    fun delete(nameTag: NameTag) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(nameTag)
        }
    }

    class NameTagModelFactory(
        private val application: Application
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