package fr.esipe.barrouxrodriguez.plutus.model.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = NoteBook::class,
            parentColumns = ["idNotebook"], childColumns = ["idNotebook"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class Transaction(
    @PrimaryKey(autoGenerate = true) val idTransaction: Int = 0,
    val title_transaction: String,
    val date_transaction: Date = Calendar.getInstance().time,
    val amount_transaction: Float,
    val description_transaction: String = "",
    val idNotebook: Int
)