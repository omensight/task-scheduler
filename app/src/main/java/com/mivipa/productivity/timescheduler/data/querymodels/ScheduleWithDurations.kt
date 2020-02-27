package com.mivipa.productivity.timescheduler.data.querymodels

import androidx.room.Embedded
import androidx.room.Relation
import com.mivipa.productivity.timescheduler.data.model.Term
import com.mivipa.productivity.timescheduler.data.model.Schedule

class ScheduleWithDurations {
    @Embedded
    lateinit var schedule: Schedule

    @Relation(entity = Term::class, entityColumn = "duration_fk_schedule", parentColumn = "schedule_id")
    lateinit var terms: List<Term>
}