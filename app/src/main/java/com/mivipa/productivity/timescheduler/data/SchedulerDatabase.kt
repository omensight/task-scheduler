package com.mivipa.productivity.timescheduler.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mivipa.productivity.timescheduler.data.model.Term
import com.mivipa.productivity.timescheduler.data.model.DurationDao
import com.mivipa.productivity.timescheduler.data.model.Schedule
import com.mivipa.productivity.timescheduler.data.model.ScheduleDao

@Database(entities = [Term::class, Schedule::class], version = 1)
@TypeConverters(com.mivipa.productivity.timescheduler.data.model.TypeConverters::class)
abstract class SchedulerDatabase: RoomDatabase(){
    abstract fun scheduleDao(): ScheduleDao
    abstract fun durationDao(): DurationDao
}