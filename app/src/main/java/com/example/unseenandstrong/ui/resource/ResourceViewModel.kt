package com.example.unseenandstrong.ui.resource

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ChecklistItem(
    val title: String,
    val points: List<String>
)

data class ValidationFact(
    val title: String,
    val description: String
)

class ResourceViewModel : ViewModel() {

    private val _checklistItems = MutableStateFlow(
        listOf(
            ChecklistItem(
                title = "FMLA Basics",
                points = listOf(
                    "Worked at least 1,250 hours in the past 12 months",
                    "Company has 50+ employees within 75 miles"
                )
            ),
            ChecklistItem(
                title = "ADA Accommodations",
                points = listOf(
                    "Condition substantially limits a major life activity",
                    "Requested accommodation is reasonable"
                )
            )
        )
    )
    val checklistItems: StateFlow<List<ChecklistItem>> = _checklistItems.asStateFlow()

    private val _validationFacts = MutableStateFlow(
        listOf(
            ValidationFact(
                title = "Invisible does not mean unreal",
                description = "Normal lab results do not invalidate experienced pain or neurological symptoms."
            ),
            ValidationFact(
                title = "Fatigue is physiological",
                description = "Chronic fatigue is a body-level energy limitation, not laziness or a character flaw."
            ),
            ValidationFact(
                title = "Function can vary day to day",
                description = "Symptoms can fluctuate; being okay for one hour does not mean someone is fully recovered."
            )
        )
    )
    val validationFacts: StateFlow<List<ValidationFact>> = _validationFacts.asStateFlow()
}

