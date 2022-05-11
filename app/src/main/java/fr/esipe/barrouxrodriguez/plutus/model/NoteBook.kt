package fr.esipe.barrouxrodriguez.plutus.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class NoteBook(var title: String) {
    @RequiresApi(Build.VERSION_CODES.O)
    private val dateCreation: LocalDateTime? = LocalDateTime.now()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun toString(): String {
        return "$title $dateCreation"
    }
}