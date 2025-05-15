package com.example.smartbottle.core.domain

interface CoreRepository {

    suspend fun postBleData(temperature: Float, pressure: Int, waterIntake: Float) : CoreResult<Unit>
}