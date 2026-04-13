package com.example.unseenandstrong.data.local.routine

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routine_tasks")
data class RoutineTaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val taskName: String,
    val isCompleted: Boolean = false
)

