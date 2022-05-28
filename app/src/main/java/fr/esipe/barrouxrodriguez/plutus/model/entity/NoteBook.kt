package fr.esipe.barrouxrodriguez.plutus.model.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.util.*

@Entity(indices = [Index("titleNoteBook", unique = true)])
data class NoteBook(
    @NotNull val titleNoteBook: String,
    val dateCreation: Date? = Calendar.getInstance().time,
    val totalAmount : Int? = 0,
    @PrimaryKey(autoGenerate = true) val idNotebook: Int = 0
)