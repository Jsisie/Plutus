package fr.esipe.barrouxrodriguez.plutus.model.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
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
data class Budget(
    val titleBudget: String,
    val amountBudget: Float,
    @Embedded @NotNull val nameTag: NameTag,
    val idNotebook: Int,
    val dateStart: Date,
    val dateEnd: Date,
    @PrimaryKey(autoGenerate = true) val idBudget: Int = 0,
)
