package com.example.smartbottle.auth.presentation.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartbottle.auth.domain.AuthRepository
import com.example.smartbottle.auth.domain.AuthResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repository: AuthRepository
): ViewModel() {
    var state by mutableStateOf(RegisterState())
        private set

    private val resultChannel  = Channel<AuthResult<Unit>>()
    val authResults = resultChannel.receiveAsFlow()

    init {
        authenticate()
    }

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
                register()
            }

        }
    }

    private fun register() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.register(state.email, state.password, state.checkPassword)
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }

    private fun authenticate(){
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.authenticate()
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }
}