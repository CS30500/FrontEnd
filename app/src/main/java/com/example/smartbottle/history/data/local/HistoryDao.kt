package com.example.smartbottle.history.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface HistoryDao {
    @Query("SELECT * FROM historyitementity")
    suspend fun getHistoryList(): List<HistoryItemEntity>

    @Query("DELETE FROM historyitementity")
    suspend fun deleteAllHistory()

    @Upsert
    suspend fun upsertHistoryList(history: List<HistoryItemEntity>)

    @Query("SELECT * FROM historyitementity WHERE date = :date")
    suspend fun getHistoryItem(date: String): HistoryItemEntity

}