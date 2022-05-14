package fr.esipe.barrouxrodriguez.plutus.model.repository

import androidx.lifecycle.LiveData
import fr.esipe.barrouxrodriguez.plutus.model.dao.NameTagDao
import fr.esipe.barrouxrodriguez.plutus.model.entity.NameTag

class NameTagRepository(private val nameTagDao: NameTagDao) {

    val readAllData: LiveData<List<NameTag>> = nameTagDao.getAll()

    suspend fun insertAll(vararg nameTag: NameTag) {
        nameTagDao.insertAll(*nameTag)
    }


    suspend fun delete(nameTag: NameTag) {
        nameTagDao.delete(nameTag)
    }
}
