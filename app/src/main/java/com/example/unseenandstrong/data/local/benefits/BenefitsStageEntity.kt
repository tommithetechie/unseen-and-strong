package com.example.unseenandstrong.data.local.benefits

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "benefits_stages")
data class BenefitsStageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val stageOrder: Int,
    val stageName: String,
    val status: String = "Pending", // Pending, In Progress, Completed
    val dateCompleted: Long? = null,
    val deadlineDate: Long? = null,
    val notes: String = ""
)

