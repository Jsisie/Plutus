package fr.esipe.barrouxrodriguez.plutus.model.entity

import androidx.room.Embedded
import androidx.room.Relation


data class FilterWithNameTags(
    @Embedded val filter: Filter,
    @Relation(
        parentColumn = "idFilter",
        entityColumn = "idFilter"
    ) val nameTags: List<NameTag>,
) {
    fun test(transactionWithNameTags: TransactionWithNameTags): Boolean {
        return filter.test(transactionWithNameTags.transaction) && transactionWithNameTags.nameTags.containsAll(
            nameTags
        )
    }

    override fun toString(): String {
        val sb = StringBuilder("")
        if (nameTags.isNotEmpty()) {
            sb.append(" contains the following tags: \n")
            nameTags.forEach { tag -> sb.append(tag.titleNameTag).append("\n") }
        }
        return filter.toString() + sb.toString()
    }
}
