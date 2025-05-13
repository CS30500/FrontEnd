package com.example.smartbottle.history.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [HistoryItemEntity::class],
    version = 1)
abstract class HistoryDatabase : RoomDatabase() {
    abstract val dao: HistoryDao
}