package com.example.smartbottle.history.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class HistoryViewModel : ViewModel() {

    var state by mutableStateOf(HistoryState())
        private set

    fun onAction(action: HistoryAction) {
        when(action) {
            is HistoryAction.LoadHistory -> {
                state = state.copy(isLoading = true)
                getHistory()
            }
            }
        }

    private fun getHistory(){

    }
}