package com.mivipa.productivity.timescheduler.data.model

import androidx.room.*
import java.util.*

@Entity(tableName = "durations", foreignKeys = [ForeignKey(entity = Schedule::class, childColumns = ["duration_fk_schedule"], parentColumns = ["schedule_id"])] )
data class Term (
    @PrimaryKey
    @ColumnInfo(name = "duration_info")
    val id: String,

    @ColumnInfo(name = "duration_fk_schedule")
    val fkScheduleId: String,

    @ColumnInfo(name = "duration_millis")
    val durationMillis: Long = 0L
){
    @Ignore
    var start: Date? = null
    @Ignore
    var end: Date? = null
}