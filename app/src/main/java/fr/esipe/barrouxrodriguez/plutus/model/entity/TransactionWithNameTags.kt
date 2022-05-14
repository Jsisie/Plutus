package fr.esipe.barrouxrodriguez.plutus.model.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TransactionWithNameTags(
    @Embedded val transaction: Transaction,
    @Relation(
        parentColumn = "idTransaction",
        entityColumn = "idTransaction"
    ) val nameTags: List<NameTag>,
)