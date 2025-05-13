package com.example.smartbottle.auth.presentation.login

data class LoginState (
    val email: String = "",
    val password: String = "",
    val isValidEmail: Boolean = false,
    val isValidPassword: Boolean = false,
    val isLoggedIn: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false
)