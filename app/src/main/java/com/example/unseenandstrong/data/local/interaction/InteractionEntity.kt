package com.example.unseenandstrong.data.local.interaction

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "interactions")
data class InteractionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: Long,
    val needsFollowUp: Boolean = false,
    val followUpDate: Long? = null,
    val category: String,
    val personName: String,
    val organization: String,
    val notes: String
)

