package com.mivipa.productivity.timescheduler.data.model

import androidx.room.Dao
import androidx.room.Query
import com.mivipa.productivity.timescheduler.data.querymodels.ScheduleWithDurations
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ScheduleDao: BaseDao<Schedule> {

    @Query("""
        SELECT * from schedules
    """)
    abstract fun getAllSchedulesFlow(): Flow<List<Schedule>>

    @Query("""
        SELECT * FROM schedules
        LEFT OUTER JOIN durations ON(schedule_id = duration_fk_schedule)
        WHERE schedule_id = :scheduleId
    """)
    abstract fun getScheduleById(scheduleId: String): Flow<ScheduleWithDurations>
}