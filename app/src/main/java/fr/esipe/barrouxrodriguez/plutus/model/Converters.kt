package fr.esipe.barrouxrodriguez.plutus.model

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun dateToLong(date: Date): Long = date.time

    @TypeConverter
    fun longToDate(value: Long): Date = Date(value)

}