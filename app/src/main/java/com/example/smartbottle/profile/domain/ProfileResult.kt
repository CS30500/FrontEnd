package com.example.smartbottle.profile.domain

sealed class ProfileResult<T>(
    val data: T? = null,
    val message: String?
) {
    class Success<T>(data: T) : ProfileResult<T>(data, null)
    class Error<T>(message: String) : ProfileResult<T>(null,  message)
}