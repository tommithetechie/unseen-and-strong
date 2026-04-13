package com.example.unseenandstrong.ui.routine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.unseenandstrong.data.local.routine.RoutineDao
import com.example.unseenandstrong.data.local.routine.RoutineTaskEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RoutineViewModel(
    private val routineDao: RoutineDao
) : ViewModel() {

    val tasks: StateFlow<List<RoutineTaskEntity>> =
        routineDao.getAllTasks().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        seedDefaultTasksIfEmpty()
    }

    fun toggleTask(task: RoutineTaskEntity) {
        viewModelScope.launch {
            routineDao.updateTaskCompletion(taskId = task.id, isCompleted = !task.isCompleted)
        }
    }

    private fun seedDefaultTasksIfEmpty() {
        viewModelScope.launch {
            if (routineDao.getAllTasks().first().isEmpty()) {
                routineDao.insertTask(RoutineTaskEntity(taskName = "Drink water"))
                routineDao.insertTask(RoutineTaskEntity(taskName = "Take meds"))
            }
        }
    }

    class Factory(
        private val routineDao: RoutineDao
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RoutineViewModel::class.java)) {
                return RoutineViewModel(routineDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}

