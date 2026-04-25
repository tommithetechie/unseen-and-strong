package com.example.unseenandstrong.ui.interaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.unseenandstrong.data.local.interaction.InteractionDao
import com.example.unseenandstrong.data.local.interaction.InteractionEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.random.Random

class InteractionViewModel(
    private val interactionDao: InteractionDao
) : ViewModel() {

    private val validationMessages = listOf(
        "Advocating for yourself is exhausting. I'm proud of you.",
        "Your voice matters. It's okay to rest now.",
        "That took a lot of energy. You did great today.",
        "You showed up for yourself in a hard moment.",
        "What you just did counts. Be gentle with yourself."
    )

    private val _showValidationOverlay = MutableStateFlow(false)
    val showValidationOverlay: StateFlow<Boolean> = _showValidationOverlay.asStateFlow()

    private val _currentValidationMessage = MutableStateFlow("")
    val currentValidationMessage: StateFlow<String> = _currentValidationMessage.asStateFlow()

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
        followUpDateMillis: Long?,
        notes: String,
        onSaved: (() -> Unit)? = null
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
                    followUpDateMillis = followUpDateMillis,
                    category = trimmedCategory,
                    personName = trimmedPersonName,
                    organization = trimmedOrganization,
                    notes = trimmedNotes
                )
            )

            _currentValidationMessage.value =
                validationMessages[Random.nextInt(validationMessages.size)]
            _showValidationOverlay.value = true
            onSaved?.invoke()
        }
    }

    fun dismissValidationOverlay() {
        _showValidationOverlay.value = false
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

