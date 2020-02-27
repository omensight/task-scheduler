package com.mivipa.productivity.timescheduler.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.room.Room
import com.mivipa.productivity.timescheduler.data.SchedulerDatabase
import com.mivipa.productivity.timescheduler.data.model.Term
import com.mivipa.productivity.timescheduler.data.model.Schedule
import com.mivipa.productivity.timescheduler.data.model.ScheduleRepository
import com.mivipa.productivity.timescheduler.data.querymodels.ScheduleWithDurations
import kotlinx.coroutines.flow.Flow

class MainViewModel(context: Application) : AndroidViewModel(context) {
    val database =
        Room.databaseBuilder(context, SchedulerDatabase::class.java, "ScheduleDB")
            .build()
    val scheduleRepository = ScheduleRepository(database)
    val schedulesList = scheduleRepository.getScheduleList()

    fun getScheduleWithDurations(scheduleId: String): Flow<ScheduleWithDurations> {
        return scheduleRepository.getScheduleWithDurations(scheduleId)
    }


    suspend fun saveTerm(term: Term){
        scheduleRepository.insertDuration(term)
    }

    suspend fun saveSchedule(schedule: Schedule){
        scheduleRepository.insertSchedule(schedule)
    }

    suspend fun updateDuration(term: Term){
        scheduleRepository.updateDuration(term)
    }

    suspend fun updateSchedule(schedule: Schedule){
        scheduleRepository.updateSchedule(schedule)
    }
}
