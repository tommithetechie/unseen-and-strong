package com.example.unseenandstrong.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.unseenandstrong.data.local.checkin.DailyCheckInDao
import com.example.unseenandstrong.data.local.checkin.DailyCheckInEntity
import com.example.unseenandstrong.data.local.journal.JournalDao
import com.example.unseenandstrong.data.local.journal.JournalEntryEntity
import com.example.unseenandstrong.data.local.routine.RoutineDao
import com.example.unseenandstrong.data.local.routine.RoutineTaskEntity
import com.example.unseenandstrong.data.local.script.ScriptDao
import com.example.unseenandstrong.data.local.script.ScriptEntity

@Database(
    entities = [
        DailyCheckInEntity::class,
        JournalEntryEntity::class,
        RoutineTaskEntity::class,
        ScriptEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class UnseenDatabase : RoomDatabase() {

    abstract fun dailyCheckInDao(): DailyCheckInDao
    abstract fun journalDao(): JournalDao
    abstract fun routineDao(): RoutineDao
    abstract fun scriptDao(): ScriptDao

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
                    .addCallback(SEED_SCRIPTS_CALLBACK)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private val SEED_SCRIPTS_CALLBACK = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                db.execSQL(
                    """
                    INSERT INTO scripts (category, title, gentleText, directText, firmText)
                    VALUES (
                        'Doctor',
                        'Requesting symptom support',
                        'I have been managing persistent symptoms, and I would really appreciate your help making a plan that feels manageable for me.',
                        'My symptoms are affecting daily function. I need clear next steps for treatment and follow-up.',
                        'These symptoms are significantly impacting my life. I need this concern documented and a concrete care plan today.'
                    )
                    """.trimIndent()
                )

                db.execSQL(
                    """
                    INSERT INTO scripts (category, title, gentleText, directText, firmText)
                    VALUES (
                        'Work',
                        'Asking for reasonable flexibility',
                        'I am committed to my role and would appreciate a small adjustment so I can keep contributing consistently.',
                        'I need a reasonable accommodation to manage my health while maintaining my work responsibilities.',
                        'I am formally requesting accommodations so I can perform essential duties safely and sustainably.'
                    )
                    """.trimIndent()
                )

                db.execSQL(
                    """
                    INSERT INTO scripts (category, title, gentleText, directText, firmText)
                    VALUES (
                        'Boundary',
                        'Protecting energy and rest',
                        'I care about this, but I need to pause and rest right now. I can revisit this when I have capacity.',
                        'I cannot take this on today. I need to protect my energy and keep my commitments realistic.',
                        'I am not available for this. Please respect that decision and do not pressure me to explain further.'
                    )
                    """.trimIndent()
                )
            }
        }
    }
}
