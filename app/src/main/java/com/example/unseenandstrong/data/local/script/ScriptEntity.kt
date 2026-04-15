package com.example.unseenandstrong.data.local.script

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scripts")
data class ScriptEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val category: String,
    val title: String,
    val gentleText: String,
    val directText: String,
    val firmText: String
)

