package com.mivipa.productivity.timescheduler.data.model

import androidx.room.TypeConverter
import java.util.*

class TypeConverters {
    @TypeConverter
    fun dateToLong(date: Date): Long{
        return date?.time
    }

    @TypeConverter
    fun longToDate(long: Long): Date{
        return long?.let {
            Date(it)
        }
    }
}