package fr.esipe.barrouxrodriguez.plutus.model.entity

import androidx.room.Embedded
import androidx.room.Relation

data class NoteBookWithTransactionsAndBudget(
    @Embedded val noteBook: NoteBook,
    @Relation(
        entity = Transaction::class,
        parentColumn = "idNotebook",
        entityColumn = "idNotebook"
    ) val listTransaction: List<TransactionWithNameTags>,
    @Relation(
        parentColumn = "idNotebook",
        entityColumn = "idNotebook"
    ) val listBudget: List<Budget>
)
