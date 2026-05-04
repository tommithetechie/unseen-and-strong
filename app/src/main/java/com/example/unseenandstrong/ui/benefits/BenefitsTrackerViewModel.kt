package com.example.unseenandstrong.ui.benefits

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.unseenandstrong.data.local.UnseenDatabase
import com.example.unseenandstrong.data.local.benefits.BenefitsStageEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BenefitsTrackerViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = UnseenDatabase.getDatabase(application).benefitsStageDao()

    val stages: StateFlow<List<BenefitsStageEntity>> = dao.getAllStages()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun updateStage(stage: BenefitsStageEntity) {
        viewModelScope.launch {
            dao.updateStage(stage)
        }
    }
}

