package fr.esipe.barrouxrodriguez.plutus.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.*

@Entity
data class NoteBook(
    @PrimaryKey(autoGenerate = true) val idNotebook: Int,
    val titleNoteBook: String,
    val dateCreation: Date? = Calendar.getInstance().time,
)