package com.example.smartbottle.core.domain

sealed class CoreResult<T>(
    val data: T? = null,
    val message: String?
) {
    class Success<T>(data: T) : CoreResult<T>(data, null)
    class Error<T>(message: String) : CoreResult<T>(null, message)
}