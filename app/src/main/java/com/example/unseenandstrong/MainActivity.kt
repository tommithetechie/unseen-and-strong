package com.example.unseenandstrong

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.unseenandstrong.data.local.UnseenDatabase
import com.example.unseenandstrong.ui.checkin.CheckInViewModel
import com.example.unseenandstrong.ui.checkin.DailyCheckInScreen

class MainActivity : ComponentActivity() {

    private val database: UnseenDatabase by lazy {
        UnseenDatabase.getDatabase(applicationContext)
    }

    private val checkInViewModel: CheckInViewModel by lazy {
        ViewModelProvider(
            this,
            CheckInViewModel.Factory(database.dailyCheckInDao())
        )[CheckInViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            DailyCheckInScreen(
                onSave = checkInViewModel::saveCheckIn
            )
        }
    }
}