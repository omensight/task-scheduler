package com.mivipa.productivity.timescheduler.data.model

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {

    @Insert
    suspend fun insert(vararg items: T)

    @Update
    suspend fun update(vararg items: T)

    @Delete
    suspend fun delete(vararg items: T)
}