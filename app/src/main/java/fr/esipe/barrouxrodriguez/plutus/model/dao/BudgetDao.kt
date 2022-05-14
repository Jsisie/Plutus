package fr.esipe.barrouxrodriguez.plutus.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import fr.esipe.barrouxrodriguez.plutus.model.entity.Budget

@Dao
interface BudgetDao {
    @Query("SELECT * FROM Budget")
    fun getAll(): LiveData<List<Budget>>

    @Insert
    suspend fun insertAll(vararg budget: Budget)

    @Delete
    suspend fun delete(budget: Budget)
}
