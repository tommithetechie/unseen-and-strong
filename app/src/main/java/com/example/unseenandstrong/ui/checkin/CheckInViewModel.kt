package com.example.unseenandstrong.ui.checkin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.unseenandstrong.data.local.checkin.DailyCheckInDao
import com.example.unseenandstrong.data.local.checkin.DailyCheckInEntity
import java.time.LocalDate
import kotlinx.coroutines.launch

class CheckInViewModel(
    private val dailyCheckInDao: DailyCheckInDao
) : ViewModel() {

    fun saveCheckIn(painLevel: Int, spoonsLevel: Int, mood: String) {
        viewModelScope.launch {
            dailyCheckInDao.insert(
                DailyCheckInEntity(
                    date = LocalDate.now().toString(),
                    painLevel = painLevel,
                    spoonsLevel = spoonsLevel,
                    mood = mood.trim()
                )
            )
        }
    }

    class Factory(
        private val dailyCheckInDao: DailyCheckInDao
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CheckInViewModel::class.java)) {
                return CheckInViewModel(dailyCheckInDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}

