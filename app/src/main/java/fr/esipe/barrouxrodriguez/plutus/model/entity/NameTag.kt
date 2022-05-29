package fr.esipe.barrouxrodriguez.plutus.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NameTag(
    val titleNameTag: String,
    val idTransaction: Int?,
    val isPredefined: Boolean = false,
    @PrimaryKey(autoGenerate = true) val idNameTag: Int = 0
)