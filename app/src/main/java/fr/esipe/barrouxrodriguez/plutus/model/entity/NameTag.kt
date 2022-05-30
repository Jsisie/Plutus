package fr.esipe.barrouxrodriguez.plutus.model.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Transaction::class,
            parentColumns = ["idTransaction"], childColumns = ["idTransaction"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class NameTag(
    val titleNameTag: String,
    val idTransaction: Int?,
    val isPredefined: Boolean = false,
    @PrimaryKey(autoGenerate = true) val idNameTag: Int = 0
)