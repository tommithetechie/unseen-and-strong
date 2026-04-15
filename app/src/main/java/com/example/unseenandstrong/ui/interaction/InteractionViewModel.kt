package com.example.unseenandstrong.ui.interaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.unseenandstrong.data.local.interaction.InteractionDao
import com.example.unseenandstrong.data.local.interaction.InteractionEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class InteractionViewModel(
    private val interactionDao: InteractionDao
) : ViewModel() {

    val interactions: StateFlow<List<InteractionEntity>> =
        interactionDao.getAllInteractions().stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )

    fun saveInteraction(
        category: String,
        personName: String,
        organization: String,
        notes: String
    ) {
        val trimmedCategory = category.trim()
        val trimmedPersonName = personName.trim()
        val trimmedOrganization = organization.trim()
        val trimmedNotes = notes.trim()

        if (trimmedCategory.isBlank() || trimmedPersonName.isBlank()) return

        viewModelScope.launch {
            interactionDao.insertInteraction(
                InteractionEntity(
                    timestamp = System.currentTimeMillis(),
                    category = trimmedCategory,
                    personName = trimmedPersonName,
                    organization = trimmedOrganization,
                    notes = trimmedNotes
                )
            )
        }
    }

    fun deleteInteraction(interaction: InteractionEntity) {
        viewModelScope.launch {
            interactionDao.deleteInteraction(interaction)
        }
    }

    class Factory(
        private val interactionDao: InteractionDao
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(InteractionViewModel::class.java)) {
                return InteractionViewModel(interactionDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}

