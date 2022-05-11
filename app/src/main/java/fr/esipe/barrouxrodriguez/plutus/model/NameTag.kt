package fr.esipe.barrouxrodriguez.plutus.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NameTag(
    @PrimaryKey(autoGenerate = true) val idNameTag: Int,
    val title: String)