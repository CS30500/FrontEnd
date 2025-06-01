//ProfileViewModel.kt

package com.example.smartbottle.profile.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartbottle.history.domain.HistoryResult
import com.example.smartbottle.profile.domain.ProfileRepository
import com.example.smartbottle.profile.domain.ProfileResult
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileRepository: ProfileRepository
) : ViewModel() {

    var state by mutableStateOf(ProfileState())
        private set

    init {
        loadProfile()
    }

    fun onAction(action: ProfileAction) {
        when (action) {
            is ProfileAction.SaveNotificationSettings -> {
                saveNotificationSettings(
                    alertTemperature = action.alertTemperature,
                    hydrationReminder = action.hydrationReminder,
                    dndStart = action.dndStart,
                    dndEnd = action.dndEnd
                )
            }

            else -> {}
        }
    }

    private fun loadProfile(){
        viewModelScope.launch {
            state = state.copy(
                isLoading = true
            )

            profileRepository.getProfile().collect { result ->
                state = when (result) {
                    is ProfileResult.Error -> {
                        state.copy(
                            isError = true
                        )

                    }

                    is ProfileResult.Success -> {
                        state.copy(
                            isError = false,
                            profile = result.data
                        )
                    }
                }
            }

            state = state.copy(
                isLoading = false
            )
        }
    }
    private fun saveNotificationSettings(
        alertTemperature: Double?,
        hydrationReminder: Int?,
        dndStart: Int?,
        dndEnd: Int?
    ) {
        viewModelScope.launch {
            val updated = state.profile?.copy(
                alertTemperature = alertTemperature,
                hydrationReminder = hydrationReminder,
                dndStart = dndStart,
                dndEnd = dndEnd
            ) ?: return@launch

            profileRepository.updateProfile(updated)

            state = state.copy(profile = updated)
        }
    }
}

