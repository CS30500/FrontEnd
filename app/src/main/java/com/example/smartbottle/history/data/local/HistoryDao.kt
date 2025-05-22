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

    @Query("""
    SELECT * FROM historyitementity 
    WHERE substr(date, 1, 4) = :year AND substr(date, 6, 2) = :month
    ORDER BY date ASC
""")
    suspend fun getHistoryByYearMonth(year: String, month: String): List<HistoryItemEntity>


}