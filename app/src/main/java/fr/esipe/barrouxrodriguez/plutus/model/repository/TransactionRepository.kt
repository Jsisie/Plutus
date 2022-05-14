package fr.esipe.barrouxrodriguez.plutus.model.repository

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import fr.esipe.barrouxrodriguez.plutus.model.entity.Transaction
import fr.esipe.barrouxrodriguez.plutus.model.entity.TransactionWithNameTags

interface TransactionRepository {

    @androidx.room.Transaction
    @Query("SELECT * FROM `Transaction`")
    suspend fun getAllWithNameTags(): LiveData<List<TransactionWithNameTags>>

    @Query("SELECT *  FROM `Transaction` WHERE idTransaction = (:transaction)")
    suspend fun loadByIdWithNameTags(transaction: Int): TransactionWithNameTags?

    @Insert
    suspend fun insertAll(vararg transaction: Transaction)

    @Delete
    suspend fun delete(transaction: Transaction)
}
