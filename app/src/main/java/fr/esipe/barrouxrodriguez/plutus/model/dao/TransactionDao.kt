package fr.esipe.barrouxrodriguez.plutus.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import fr.esipe.barrouxrodriguez.plutus.model.entity.Transaction
import fr.esipe.barrouxrodriguez.plutus.model.entity.TransactionWithNameTags

@Dao
interface TransactionDao {

    @androidx.room.Transaction
    @Query("SELECT * FROM `Transaction`")
    fun getAllWithNameTags(): LiveData<List<TransactionWithNameTags>>

    @Query("SELECT *  FROM `Transaction` WHERE idTransaction = (:transaction)")
    fun loadByIdWithNameTags(transaction: Int): TransactionWithNameTags?

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(vararg transaction: Transaction)

    @Delete
    suspend fun delete(transaction: Transaction)
}
