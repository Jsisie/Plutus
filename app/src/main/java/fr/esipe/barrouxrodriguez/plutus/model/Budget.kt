package fr.esipe.barrouxrodriguez.plutus.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Budget(
    @PrimaryKey(autoGenerate = true) val idBudget: Int,
    val title: String,
    val amount: Int,
    @Embedded val nameTag: NameTag,
    val dateStart: LocalDate,
    val dateEnd: LocalDate)
