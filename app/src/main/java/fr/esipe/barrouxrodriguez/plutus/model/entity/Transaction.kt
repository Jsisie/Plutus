package fr.esipe.barrouxrodriguez.plutus.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Transaction(
    @PrimaryKey(autoGenerate = true) val idTransaction: Int,
    val title_transaction: String,
    val date_transaction: Date = Calendar.getInstance().time,
    val amount_transaction: Int,
    val idNotebook: Int?
)