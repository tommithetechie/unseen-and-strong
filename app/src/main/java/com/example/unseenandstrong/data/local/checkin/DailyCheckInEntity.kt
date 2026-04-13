package com.example.unseenandstrong.data.local.checkin

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "daily_check_ins",
    indices = [Index(value = ["entry_date"], unique = true)]
)
data class DailyCheckInEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    // Use ISO-8601 format (yyyy-MM-dd) for predictable local date queries.
    @ColumnInfo(name = "entry_date")
    val date: String,
    @ColumnInfo(name = "pain_level")
    val painLevel: Int,
    @ColumnInfo(name = "spoons_level")
    val spoonsLevel: Int,
    val mood: String
) {
    init {
        require(painLevel in 1..10) { "painLevel must be between 1 and 10" }
        require(spoonsLevel in 1..10) { "spoonsLevel must be between 1 and 10" }
    }
}

