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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NameTag

        if (titleNameTag != other.titleNameTag) return false
        if (idTransaction != other.idTransaction) return false
        if (isPredefined != other.isPredefined) return false

        return true
    }

    override fun hashCode(): Int {
        var result = titleNameTag.hashCode()
        result = 31 * result + (idTransaction ?: 0)
        result = 31 * result + isPredefined.hashCode()
        return result
    }

}