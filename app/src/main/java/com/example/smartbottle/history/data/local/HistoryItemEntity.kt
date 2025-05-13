package com.example.smartbottle.history.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
data class HistoryItemEntity(
    @PrimaryKey(autoGenerate = false)
    val date: String?,

    val target_ml: Double?,
    val total_intake_ml: Double?
)