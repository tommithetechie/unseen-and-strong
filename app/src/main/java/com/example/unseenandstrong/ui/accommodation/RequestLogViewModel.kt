package com.example.unseenandstrong.ui.accommodation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.unseenandstrong.data.local.UnseenDatabase
import com.example.unseenandstrong.data.local.accommodation.AccommodationRequestEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RequestLogViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = UnseenDatabase.getDatabase(application).accommodationRequestDao()

    val requests: StateFlow<List<AccommodationRequestEntity>> = dao.getAllRequests()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addRequest(requestType: String, status: String, notes: String) {
        viewModelScope.launch {
            dao.insertRequest(
                AccommodationRequestEntity(
                    requestType = requestType,
                    status = status,
                    notes = notes,
                    submissionDate = System.currentTimeMillis()
                )
            )
        }
    }
}

