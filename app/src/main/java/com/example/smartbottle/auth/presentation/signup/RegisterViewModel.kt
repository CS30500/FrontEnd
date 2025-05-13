package com.example.smartbottle.auth.presentation.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class RegisterViewModel: ViewModel() {
    var state by mutableStateOf(RegisterState())
        private set

    fun onAction(action: RegisterAction) {
        when (action) {
            is RegisterAction.ChangeEmail -> {
                state = state.copy(email = action.newEmail)
            }

            is RegisterAction.ChangePassword -> {
                state = state.copy(password = action.newPassword)
            }

            is RegisterAction.ChangeCheckPassword -> {
                state = state.copy(checkPassword = action.newCheckPassword)
            }

            is RegisterAction.Register -> {

            }

        }
    }
}