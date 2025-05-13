package com.example.smartbottle.auth.presentation.signup

sealed interface RegisterAction {
    data class ChangeEmail(val newEmail: String) : RegisterAction
    data class ChangePassword(val newPassword: String) : RegisterAction
    data class ChangeCheckPassword(val newCheckPassword: String) : RegisterAction
    data object Register : RegisterAction
}