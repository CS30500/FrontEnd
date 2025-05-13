package com.example.smartbottle.history.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartbottle.history.domain.HistoryRepository
import com.example.smartbottle.history.domain.HistoryResult
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val historyRepository: HistoryRepository
) : ViewModel() {

    var state by mutableStateOf(HistoryState())
        private set

    init{
        loadHistory()
    }

    fun onAction(action: HistoryAction) {

    }



    private fun loadHistory(){
        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )

            historyRepository.getHistory().collect{ result ->
                when(result){
                    is HistoryResult.Error -> {
                        state = state.copy(
                            isError = true
                        )

                    }
                    is HistoryResult.Success -> {
                        state = state.copy(
                            isError = false,
                            historyList = result.data?.history ?: emptyList()
                            )
                    }
                }
            }

            state = state.copy(
                isLoading = false
            )
        }
    }
}