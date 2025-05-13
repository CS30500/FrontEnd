package com.example.smartbottle.auth.presentation.signup

data class RegisterState (
    val email: String = "",
    val password: String = "",
    val checkPassword: String = "",
    val isValidEmail: Boolean = false,
    val isValidPassword: Boolean = false,
    val isValidCheckPassword: Boolean = false,
    val isRegistered: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false
)