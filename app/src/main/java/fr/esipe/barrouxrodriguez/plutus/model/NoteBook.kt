package fr.esipe.barrouxrodriguez.plutus.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class NoteBook(
    @PrimaryKey(autoGenerate = true) val idNotebook: Int,
    val title: String,
    val listNameTag: List<NameTag>,
    val listBudget: List<Budget>,
    val dateCreation: LocalDateTime? = LocalDateTime.now(),
)