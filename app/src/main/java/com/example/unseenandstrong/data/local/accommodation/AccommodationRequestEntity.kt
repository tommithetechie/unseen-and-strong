package com.example.unseenandstrong.data.local.accommodation

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accommodation_requests")
data class AccommodationRequestEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val requestType: String,
    val submissionDate: Long,
    val status: String,
    val notes: String
)

