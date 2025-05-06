package com.example.smartbottle.auth.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

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
            state = state.copy(isLoggedIn = true)
        }
    }

}