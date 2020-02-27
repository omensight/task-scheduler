package com.mivipa.productivity.timescheduler.data.model

import com.mivipa.productivity.timescheduler.data.SchedulerDatabase
import com.mivipa.productivity.timescheduler.data.querymodels.ScheduleWithDurations
import kotlinx.coroutines.flow.Flow

class ScheduleRepository(private val mSchedulerDatabase: SchedulerDatabase) {
    private val scheduleDao = mSchedulerDatabase.scheduleDao()
    private val durationDao = mSchedulerDatabase.durationDao()

    suspend fun insertSchedule(schedule: Schedule){
        scheduleDao.insert(schedule)
    }

    suspend fun insertDuration(Term: Term){
        durationDao.insert(Term)
    }

    suspend fun updateSchedule(schedule: Schedule){
        scheduleDao.update(schedule)
    }

    suspend fun updateDuration(Term: Term){
        durationDao.update(Term)
    }

    fun getScheduleList(): Flow<List<Schedule>> {
        return scheduleDao.getAllSchedulesFlow()
    }

    fun getScheduleWithDurations(scheduleId: String): Flow<ScheduleWithDurations> {
        return scheduleDao.getScheduleById(scheduleId)
    }
}