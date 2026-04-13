package com.example.unseenandstrong.data.local.routine

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: RoutineTaskEntity): Long

    @Query("SELECT * FROM routine_tasks ORDER BY id ASC")
    fun getAllTasks(): Flow<List<RoutineTaskEntity>>

    @Query("UPDATE routine_tasks SET isCompleted = :isCompleted WHERE id = :taskId")
    suspend fun updateTaskCompletion(taskId: Long, isCompleted: Boolean)
}

