package fr.esipe.barrouxrodriguez.plutus.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class NoteBook(
    @PrimaryKey(autoGenerate = true) val idNotebook: Int,
    val titleNoteBook: String,
    val dateCreation: Date? = Calendar.getInstance().time,
)