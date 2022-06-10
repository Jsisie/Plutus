package fr.esipe.barrouxrodriguez.plutus.model.viewmodel

import android.app.Application
import androidx.lifecycle.*
import fr.esipe.barrouxrodriguez.plutus.model.NoteBookDatabase
import fr.esipe.barrouxrodriguez.plutus.model.dao.FilterDao
import fr.esipe.barrouxrodriguez.plutus.model.dao.NameTagDao
import fr.esipe.barrouxrodriguez.plutus.model.dao.TransactionDao
import fr.esipe.barrouxrodriguez.plutus.model.entity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.streams.toList

class FilterViewModel(application: Application) : AndroidViewModel(application) {
    val readAllData: LiveData<List<FilterWithNameTags>>
    private val filterDao: FilterDao =
        NoteBookDatabase.getInstance(application).FilterDao()
    private val nameTagDao: NameTagDao = NoteBookDatabase.getInstance(application).NameTagDao()

    init {
        readAllData = filterDao.getAllWithNameTags()
    }

    fun findFilterById(idFilter: Int): LiveData<FilterWithNameTags> {
        return filterDao.loadByIdWithNameTags(idFilter)
    }

    fun insert(filter: Filter) {
        viewModelScope.launch(Dispatchers.IO) {
            filterDao.insert(filter)
        }
    }

    fun insertWithStrings(filter: Filter, nameTagsList: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = filterDao.insert(filter)
            val arrayNT = nameTagsList.stream().map { tag -> NameTag(tag, idFilter = id.toInt()) }.toList()
                .toTypedArray()

            nameTagDao.insertAll(*arrayNT)
        }
    }

    fun insertWithNameTags(filter: Filter, nameTagsList: List<NameTag>) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = filterDao.insert(filter)
            nameTagDao.insertAll(*nameTagsList.toTypedArray())
        }
    }

    fun delete(filter: Filter) {
        viewModelScope.launch(Dispatchers.IO) {
            filterDao.delete(filter)
        }
    }

    class FilterModelFactory(
        private val application: Application,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            if (modelClass.isAssignableFrom(FilterViewModel::class.java)) {
                return FilterViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}