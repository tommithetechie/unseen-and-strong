package com.example.unseenandstrong.ui.journal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.unseenandstrong.data.local.journal.JournalDao
import com.example.unseenandstrong.data.local.journal.JournalEntryEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class JournalViewModel(
    private val journalDao: JournalDao
) : ViewModel() {

    val entries: StateFlow<List<JournalEntryEntity>> =
        journalDao.getAllJournalEntries().stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )

    fun saveUnseenWin(content: String) {
        saveEntry(content = content, isUnseenWin = true)
    }

    fun saveJournalEntry(content: String) {
        saveEntry(content = content, isUnseenWin = false)
    }

    private fun saveEntry(content: String, isUnseenWin: Boolean) {
        val trimmedContent = content.trim()
        if (trimmedContent.isBlank()) return

        viewModelScope.launch {
            journalDao.insertJournalEntry(
                JournalEntryEntity(
                    timestamp = System.currentTimeMillis(),
                    content = trimmedContent,
                    isUnseenWin = isUnseenWin
                )
            )
        }
    }

    class Factory(
        private val journalDao: JournalDao
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(JournalViewModel::class.java)) {
                return JournalViewModel(journalDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}

