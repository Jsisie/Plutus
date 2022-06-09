package fr.esipe.barrouxrodriguez.plutus.model.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Transaction::class,
            parentColumns = ["idTransaction"], childColumns = ["idTransaction"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class NameTag(
    val titleNameTag: String,
    val idTransaction: Int?,
    val isPredefined: Boolean = false,
    @PrimaryKey(autoGenerate = true) val idNameTag: Int = 0
) : Comparable<NameTag> {
    override fun compareTo(other: NameTag): Int {
        return titleNameTag.compareTo(other.titleNameTag)
    }

    fun copyWithNewTransaction(idTransaction: Int): NameTag{
        return NameTag(titleNameTag, idTransaction, false)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NameTag

        if (titleNameTag != other.titleNameTag) return false

        return true
    }

    override fun hashCode(): Int {
        return titleNameTag.hashCode()
    }
}