package com.mivipa.productivity.timescheduler.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "schedules")
data class Schedule(
    @PrimaryKey
    @ColumnInfo(name = "schedule_id")
    var id: String,

    @ColumnInfo(name = "schedule_name")
    var name: String,

    @ColumnInfo(name = "schedule_start_date")
    var startDate: Date
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Schedule

        if (id != other.id) return false
        if (name != other.name) return false
        if (startDate != other.startDate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + startDate.hashCode()
        return result
    }
}

