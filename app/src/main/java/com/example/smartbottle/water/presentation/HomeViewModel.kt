package com.example.smartbottle.water.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartbottle.water.domain.HomeRepository
import com.example.smartbottle.water.domain.HomeResult
import kotlinx.coroutines.launch

class HomeViewModel (
    private val homeRepository: HomeRepository

): ViewModel() {

    var state by mutableStateOf(HomeState())
        private set

    init {
        loadDailyHydration()
    }

    fun onAction(action: HomeAction) {
    }

    private fun loadDailyHydration() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )

            homeRepository.getDailyHydration().collect { result ->
                state = when (result) {
                    is HomeResult.Error -> {
                        state.copy(
                            isError = true
                        )
                    }

                    is HomeResult.Success -> {
                        state.copy(
                            isError = false,
                            dailyHydration = result.data
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