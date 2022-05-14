package fr.esipe.barrouxrodriguez.plutus.model.viewmodel

import android.app.Application
import androidx.lifecycle.*
import fr.esipe.barrouxrodriguez.plutus.model.NoteBookDatabase
import fr.esipe.barrouxrodriguez.plutus.model.dao.BudgetDao
import fr.esipe.barrouxrodriguez.plutus.model.entity.Budget
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BudgetViewModel(application: Application) : AndroidViewModel(application)  {
    private val readAllData: LiveData<List<Budget>>
    private var budgetDao: BudgetDao = NoteBookDatabase.getInstance(application).BudgetDao()

    init {
        readAllData =  budgetDao.getAll()
    }

    fun insertAll(vararg budget: Budget) {
        viewModelScope.launch(Dispatchers.IO) {
            budgetDao.insertAll(*budget)
        }
    }

    fun delete(budget: Budget) {
        viewModelScope.launch(Dispatchers.IO) {
            budgetDao.delete(budget)
        }
    }

    class BudgetModelFactory(
        private val application: Application
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            if (modelClass.isAssignableFrom(BudgetViewModel::class.java)) {
                return BudgetViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}