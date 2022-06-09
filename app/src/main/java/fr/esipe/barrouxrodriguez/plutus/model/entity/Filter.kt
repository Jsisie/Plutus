package fr.esipe.barrouxrodriguez.plutus.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Filter(
    val title: String?,
    val amountSuperior: Float?,
    val amountEqual: Float?,
    val amountInferior: Float?,
    val dateAfter: Date?,
    val dateEqual: Date?,
    val dateBefore: Date?,
    @PrimaryKey(autoGenerate = true) val idFilter: Int = 0,
) {
    fun test(transaction: Transaction): Boolean {
        title?.let {
            if (!transaction.title_transaction.contains(title))
                return false
        }
        amountSuperior?.let {
            if (amountSuperior <= transaction.amount_transaction) {
                return false
            }
        }
        amountEqual?.let {
            if (amountEqual != transaction.amount_transaction) {
                return false
            }
        }
        amountInferior?.let {
            if (amountInferior >= transaction.amount_transaction) {
                return false
            }
        }
        dateAfter?.let {
            if (dateAfter.before(transaction.date_transaction)) {
                return false
            }
        }
        dateEqual?.let {
            if (dateEqual != transaction.date_transaction) {
                return false
            }
        }
        dateBefore?.let {
            if (dateBefore.after(transaction.date_transaction)) {
                return false
            }
        }

        return true
    }

    override fun toString(): String {
        val sb = StringBuilder()
        title?.let {
            sb.append("Contains $title, ")
        }
        amountSuperior?.let {
            sb.append("amount superior than $amountSuperior, ")
        }
        amountEqual?.let {
            sb.append("amount equals to $amountEqual, ")
        }
        amountInferior?.let {
            sb.append("amount inferior than $amountInferior, ")
        }
        dateAfter?.let {
            sb.append("date after than $dateAfter, ")
        }
        dateEqual?.let {
            sb.append("date equals as $dateEqual, ")
        }
        dateBefore?.let {
            sb.append("date before than $dateBefore, ")
        }

        return sb.toString()
    }
}
