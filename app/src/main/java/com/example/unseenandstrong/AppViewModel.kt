package com.example.unseenandstrong

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class AppViewModel : ViewModel() {
    val isFlareDayActive = MutableStateFlow(false)

    fun toggleFlareDayMode() {
        isFlareDayActive.value = !isFlareDayActive.value
    }
}

