package com.example.smartbottle.core.domain

interface CoreRepository {

    suspend fun postBleTempData(temperature: Float) : CoreResult<Unit>
    suspend fun postBleDistanceData(distance: Float) : CoreResult<Unit>
    fun bleConnect()
    fun bleDisconnect()
    fun sendCommandToDevice(command: String)
    suspend fun  postFcmToken(fcmToken: String): CoreResult<Unit>
}