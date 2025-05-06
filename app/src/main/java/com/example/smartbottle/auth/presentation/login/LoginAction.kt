package com.example.smartbottle.auth.presentation.login

sealed interface LoginAction {
    data class ChangeEmail(val newEmail: String) : LoginAction
    data class ChangePassword(val newPassword: String) : LoginAction
    data object Login : LoginAction

}