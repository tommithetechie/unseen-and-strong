package com.example.unseenandstrong.ui.accommodation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class AccommodationViewModel : ViewModel() {

    private val _managerName = MutableStateFlow("")
    val managerName: StateFlow<String> = _managerName.asStateFlow()

    private val _condition = MutableStateFlow("")
    val condition: StateFlow<String> = _condition.asStateFlow()

    private val _requestedAccommodation = MutableStateFlow("")
    val requestedAccommodation: StateFlow<String> = _requestedAccommodation.asStateFlow()

    private val _duration = MutableStateFlow("")
    val duration: StateFlow<String> = _duration.asStateFlow()

    val generatedEmail: StateFlow<String> =
        combine(
            _managerName,
            _condition,
            _requestedAccommodation,
            _duration
        ) { managerName, condition, requestedAccommodation, duration ->
            val managerValue = managerName.trim()
            val conditionValue = condition.trim()
            val accommodationValue = requestedAccommodation.trim()
            val durationValue = duration.trim()

            if (
                managerValue.isBlank() &&
                conditionValue.isBlank() &&
                accommodationValue.isBlank() &&
                durationValue.isBlank()
            ) {
                ""
            } else {
                val managerText = managerValue.ifBlank { "Manager" }
                val conditionText = conditionValue.ifBlank { "a chronic health condition" }
                val accommodationText = accommodationValue.ifBlank { "a reasonable workplace accommodation" }
                val durationText = durationValue.ifBlank { "the coming weeks" }

                """
                Dear $managerText,

                I am writing to formally request a reasonable accommodation under the ADA for $conditionText.
                Specifically, I am requesting $accommodationText for $durationText.

                This adjustment will help me continue meeting my responsibilities while managing my health needs.
                I appreciate your support and am open to discussing any details needed to finalize this request.

                Sincerely,
                [Your Name]
                """.trimIndent()
            }
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = ""
            )

    fun updateManagerName(value: String) {
        _managerName.value = value
    }

    fun updateCondition(value: String) {
        _condition.value = value
    }

    fun updateRequestedAccommodation(value: String) {
        _requestedAccommodation.value = value
    }

    fun updateDuration(value: String) {
        _duration.value = value
    }

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AccommodationViewModel::class.java)) {
                return AccommodationViewModel() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}

