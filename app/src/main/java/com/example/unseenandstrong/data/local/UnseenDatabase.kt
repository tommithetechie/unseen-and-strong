package com.example.unseenandstrong.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.unseenandstrong.data.local.checkin.DailyCheckInDao
import com.example.unseenandstrong.data.local.checkin.DailyCheckInEntity
import com.example.unseenandstrong.data.local.journal.JournalDao
import com.example.unseenandstrong.data.local.journal.JournalEntryEntity
import com.example.unseenandstrong.data.local.routine.RoutineDao
import com.example.unseenandstrong.data.local.routine.RoutineTaskEntity

@Database(
    entities = [DailyCheckInEntity::class, JournalEntryEntity::class, RoutineTaskEntity::class],
    version = 3,
    exportSchema = false
)
abstract class UnseenDatabase : RoomDatabase() {

    abstract fun dailyCheckInDao(): DailyCheckInDao
    abstract fun journalDao(): JournalDao
    abstract fun routineDao(): RoutineDao

    companion object {
        @Volatile
        private var INSTANCE: UnseenDatabase? = null

        fun getDatabase(context: Context): UnseenDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UnseenDatabase::class.java,
                    "unseen_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
