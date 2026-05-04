package com.example.unseenandstrong.data.local.accommodation

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AccommodationRequestDao {
    @Query("SELECT * FROM accommodation_requests ORDER BY submissionDate DESC")
    fun getAllRequests(): Flow<List<AccommodationRequestEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRequest(request: AccommodationRequestEntity): Long

    @Update
    suspend fun updateRequest(request: AccommodationRequestEntity): Int

    @Delete
    suspend fun deleteRequest(request: AccommodationRequestEntity): Int
}
