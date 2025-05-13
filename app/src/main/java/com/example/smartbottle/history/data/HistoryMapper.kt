package com.example.smartbottle.history.data

import com.example.smartbottle.history.data.local.HistoryItemEntity
import com.example.smartbottle.history.data.remote.HistoryItemDto
import com.example.smartbottle.history.data.remote.HistoryListDto
import com.example.smartbottle.history.domain.HistoryItem
import com.example.smartbottle.history.domain.HistoryList

// HistoryListDto를 HistoryList로 변환하는 함수
fun HistoryListDto.toHistoryList(): HistoryList {

    return HistoryList(
        history = history?.map { it.toHistoryItem() } ?: emptyList()
    )

}

// HistoryItemDto를 HistoryItem으로 변환하는 함수
fun HistoryItemDto.toHistoryItem(): HistoryItem {
    return HistoryItem(
        date = date,
        target_ml = target_ml,
        total_intake_ml = total_intake_ml
    )
}

// HistoryItemEntity를 HistoryItem으로 변환하는 함수
fun HistoryItemEntity.toHistoryItem(): HistoryItem {
    return HistoryItem(
        date = date,
        target_ml = target_ml,
        total_intake_ml = total_intake_ml
    )
}

// HistoryItem을 HistoryItemEntity로 변환하는 함수
fun HistoryItem.toHistoryItemEntity(): HistoryItemEntity {
    return HistoryItemEntity(
        date = date,
        target_ml = target_ml,
        total_intake_ml = total_intake_ml
    )
}