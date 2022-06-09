package fr.esipe.barrouxrodriguez.plutus.model.viewmodel

import android.app.Application
import androidx.lifecycle.*
import fr.esipe.barrouxrodriguez.plutus.model.NoteBookDatabase
import fr.esipe.barrouxrodriguez.plutus.model.dao.NameTagDao
import fr.esipe.barrouxrodriguez.plutus.model.dao.TransactionDao
import fr.esipe.barrouxrodriguez.plutus.model.entity.NameTag
import fr.esipe.barrouxrodriguez.plutus.model.entity.Transaction
import fr.esipe.barrouxrodriguez.plutus.model.entity.TransactionWithNameTags
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.streams.toList

class TransactionViewModel(application: Application) : AndroidViewModel(application) {
    val readAllData: LiveData<List<TransactionWithNameTags>>
    private val transactionDao: TransactionDao =
        NoteBookDatabase.getInstance(application).TransactionDao()
    private val nameTagDao: NameTagDao = NoteBookDatabase.getInstance(application).NameTagDao()

    init {
        readAllData = transactionDao.getAllWithNameTags()
    }

    fun findTransactionsById(idTransaction: Int): LiveData<TransactionWithNameTags> {
        return transactionDao.loadByIdWithNameTags(idTransaction)
    }

    fun insertAll(vararg transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            transactionDao.insertAll(*transaction)
        }
    }

    fun insertWithStrings(transaction: Transaction, nameTagsList: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = transactionDao.insertAll(transaction)[0]
            val arrayNT = nameTagsList.stream().map { tag -> NameTag(tag, id.toInt()) }.toList()
                .toTypedArray()

            nameTagDao.insertAll(*arrayNT)
        }
    }

    fun insertWithNameTags(transaction: Transaction, nameTagsList: List<NameTag>) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = transactionDao.insertAll(transaction)[0]
            nameTagDao.insertAll(*nameTagsList.toTypedArray())
        }
    }

    fun delete(transaction: TransactionWithNameTags) {
        viewModelScope.launch(Dispatchers.IO) {
            transactionDao.delete(transaction.transaction)
        }
    }

    class TransactionModelFactory(
        private val application: Application,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            if (modelClass.isAssignableFrom(TransactionViewModel::class.java)) {
                return TransactionViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}