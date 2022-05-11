package fr.esipe.barrouxrodriguez.plutus.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.w3c.dom.Text
import java.time.LocalDateTime

@Entity
class Transaction(
    @PrimaryKey(autoGenerate = true) val idTransaction: Int,
    val title: Text,
    val date: LocalDateTime,
    val amount: Int,
    val listNameTag: List<NameTag>,
) {

}