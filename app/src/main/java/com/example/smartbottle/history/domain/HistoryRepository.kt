package com.example.smartbottle.history.domain

import kotlinx.coroutines.flow.Flow

interface HistoryRepository {

    suspend fun getHistory(): Flow<HistoryResult<HistoryList>>

}