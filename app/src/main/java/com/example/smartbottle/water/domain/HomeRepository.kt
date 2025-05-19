package com.example.smartbottle.water.domain

import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getDailyHydration(): Flow<HomeResult<DailyHydration>>
}