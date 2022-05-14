package fr.esipe.barrouxrodriguez.plutus.model

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class Converters {
    @TypeConverter
    fun dateToLong(date: Date): Long = date.time

    @TypeConverter
    fun longToDate(value: Long): Date = Date(value)

    companion object {
        fun printDate(date: Date, pattern: String): String {
            val simpleDateFormat = SimpleDateFormat(pattern, Locale.FRANCE)
            return simpleDateFormat.format(date)
        }
    }
}