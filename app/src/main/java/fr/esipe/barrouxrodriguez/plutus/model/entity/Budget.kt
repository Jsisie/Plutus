package fr.esipe.barrouxrodriguez.plutus.model.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.*

@Entity
data class Budget(
    @PrimaryKey(autoGenerate = true) val idBudget: Int,
    val titleBudget: String,
    val amountBudget: Int,
    @Embedded val nameTag: NameTag,
    val idNotebook: Int,
    val dateStart: Date,
    val dateEnd: Date,
)
