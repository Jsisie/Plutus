package fr.esipe.barrouxrodriguez.plutus.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import fr.esipe.barrouxrodriguez.plutus.model.entity.*

@Dao
interface FilterDao {

    @androidx.room.Transaction
    @Query("SELECT * FROM `Filter`")
    fun getAllWithNameTags(): LiveData<List<FilterWithNameTags>>

    @androidx.room.Transaction
    @Query("SELECT *  FROM `Filter` WHERE idFilter = (:idFilter)")
    fun loadByIdWithNameTags(idFilter: Int): LiveData<FilterWithNameTags>

    @Insert(onConflict = REPLACE)
    suspend fun insert(filter: Filter): Long

    @Insert(onConflict = REPLACE)
    suspend fun insertWithNameTags(filter: Filter, vararg nameTags: NameTag)

    @Delete
    suspend fun delete(filter: Filter)
}
