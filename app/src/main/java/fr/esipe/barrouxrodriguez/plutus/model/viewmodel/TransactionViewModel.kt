package fr.esipe.barrouxrodriguez.plutus.model.viewmodel

import android.app.Application
import androidx.lifecycle.*
import fr.esipe.barrouxrodriguez.plutus.model.NoteBookDatabase
import fr.esipe.barrouxrodriguez.plutus.model.dao.TransactionDao
import fr.esipe.barrouxrodriguez.plutus.model.entity.Transaction
import fr.esipe.barrouxrodriguez.plutus.model.entity.TransactionWithNameTags
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionViewModel(application: Application) : AndroidViewModel(application) {
    private val readAllData: LiveData<List<TransactionWithNameTags>>
    private val transactionDao: TransactionDao =
        NoteBookDatabase.getInstance(application).TransactionDao()

    init {
        readAllData = transactionDao.getAllWithNameTags()
    }

    fun insertAll(vararg transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            transactionDao.insertAll(*transaction)
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