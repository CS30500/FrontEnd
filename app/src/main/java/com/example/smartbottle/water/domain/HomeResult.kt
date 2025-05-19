package com.example.smartbottle.water.domain

sealed  class HomeResult<T>(
    val data: T? = null,
    val message: String?
){
    class Success<T>(data: T) : HomeResult<T>(data, null)
    class Error<T>(message: String) : HomeResult<T>(null, message)
}