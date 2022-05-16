package fr.esipe.barrouxrodriguez.plutus.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.util.*

@Entity
data class NoteBook(
    val titleNoteBook: String,
    val dateCreation: Date? = Calendar.getInstance().time,
    @PrimaryKey(autoGenerate = true) val idNotebook: Int = 0
)