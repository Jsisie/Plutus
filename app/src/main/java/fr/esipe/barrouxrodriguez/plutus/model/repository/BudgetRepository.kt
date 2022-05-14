package fr.esipe.barrouxrodriguez.plutus.model.repository

import androidx.lifecycle.LiveData
import fr.esipe.barrouxrodriguez.plutus.model.dao.BudgetDao
import fr.esipe.barrouxrodriguez.plutus.model.entity.Budget

class BudgetRepository(private val budgetDao: BudgetDao) {

    val readAllData: LiveData<List<Budget>> = budgetDao.getAll()

    suspend fun insertAll(vararg budget: Budget) {
        budgetDao.insertAll(*budget)
    }


    suspend fun delete(budget: Budget) {
        budgetDao.delete(budget)
    }
}
