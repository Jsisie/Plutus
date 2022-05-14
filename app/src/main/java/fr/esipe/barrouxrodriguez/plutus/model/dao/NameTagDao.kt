package fr.esipe.barrouxrodriguez.plutus.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import fr.esipe.barrouxrodriguez.plutus.model.entity.NameTag
@Dao
interface NameTagDao {
    @Query("SELECT * FROM NameTag")
    fun getAll(): LiveData<List<NameTag>>

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(vararg nameTag: NameTag)

    @Delete
    suspend fun delete(nameTag: NameTag)
}
