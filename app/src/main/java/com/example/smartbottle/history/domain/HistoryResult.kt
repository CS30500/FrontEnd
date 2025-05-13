package com.example.smartbottle.history.domain

sealed class HistoryResult<T>(
    val data: T? = null,
    val message: String?
) {
    class Success<T>(data: T) : HistoryResult<T>(data, null)
    class Error<T>(message: String) : HistoryResult<T>(null,  message)
}