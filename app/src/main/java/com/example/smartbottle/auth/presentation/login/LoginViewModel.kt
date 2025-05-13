package com.example.smartbottle.auth.presentation.login

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

class LoginViewModel(
    private val repository: AuthRepository
): ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    private val resultChannel  = Channel<AuthResult<Unit>>()
    val authResults = resultChannel.receiveAsFlow()

    init {
        authenticate()
    }

    fun onAction(action: LoginAction) {
        when(action) {
            is LoginAction.ChangeEmail -> {
                state = state.copy(email = action.newEmail)
            }
            is LoginAction.ChangePassword -> {
                state = state.copy(password = action.newPassword)
            }
            is LoginAction.Login -> {
                login()
                }
            }
    }

    private fun login() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.login(state.email, state.password)
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