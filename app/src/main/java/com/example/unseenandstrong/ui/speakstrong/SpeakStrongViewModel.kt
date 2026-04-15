package com.example.unseenandstrong.ui.speakstrong

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.unseenandstrong.data.local.script.ScriptDao
import com.example.unseenandstrong.data.local.script.ScriptEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class SpeakStrongViewModel(
    scriptDao: ScriptDao
) : ViewModel() {

    enum class Tone {
        GENTLE,
        DIRECT,
        FIRM
    }

    private val _selectedTone = MutableStateFlow(Tone.GENTLE)
    val selectedTone: StateFlow<Tone> = _selectedTone.asStateFlow()

    private val _selectedCategory = MutableStateFlow(CATEGORY_ALL)
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    val scripts: StateFlow<List<ScriptEntity>> =
        combine(_selectedTone, _selectedCategory, scriptDao.getAllScripts()) { _, category, allScripts ->
            if (category == CATEGORY_ALL) {
                allScripts
            } else {
                allScripts.filter { it.category == category }
            }
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun setTone(tone: Tone) {
        _selectedTone.value = tone
    }

    fun setCategory(category: String) {
        val normalized = category.trim()
        _selectedCategory.value = if (normalized.isBlank()) CATEGORY_ALL else normalized
    }

    class Factory(
        private val scriptDao: ScriptDao
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SpeakStrongViewModel::class.java)) {
                return SpeakStrongViewModel(scriptDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        const val CATEGORY_ALL = "All"
        const val CATEGORY_DOCTOR = "Doctor"
        const val CATEGORY_WORK = "Work"
        const val CATEGORY_BOUNDARY = "Boundary"
    }
}




