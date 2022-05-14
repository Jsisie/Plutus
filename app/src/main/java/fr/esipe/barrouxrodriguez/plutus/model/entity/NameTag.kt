package fr.esipe.barrouxrodriguez.plutus.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NameTag(
    @PrimaryKey val titleNameTag: String,
    val idTransaction: Int?
)