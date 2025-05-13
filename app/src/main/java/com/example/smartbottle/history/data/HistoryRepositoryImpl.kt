package com.example.smartbottle.history.data

import com.example.smartbottle.history.data.local.HistoryDao
import com.example.smartbottle.history.data.remote.HistoryListDto
import com.example.smartbottle.history.domain.HistoryList
import com.example.smartbottle.history.domain.HistoryRepository
import com.example.smartbottle.history.domain.HistoryResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HistoryRepositoryImpl(
    private val httpClient: HttpClient,
    private val dao: HistoryDao
) : HistoryRepository {

    private val tag = "NewsRepository: "

    private val baseUrl = "http://127.0.0.1:8000"

    private suspend fun getLocalHistory(): HistoryList{
        val localHistory = dao.getHistoryList()
        println(tag + "getLocalHistory: $localHistory")

        val historyList = HistoryList(
            history = localHistory.map { it.toHistoryItem() }
        )

        return historyList
    }

    private suspend fun getRemoteHistory(): HistoryList{
        val historyListDto : HistoryListDto = httpClient.get("$baseUrl/hydration/monthly").body()
        println(tag + "getRemoteHistory: $historyListDto")
        return historyListDto.toHistoryList()
    }

    override suspend fun getHistory(): Flow<HistoryResult<HistoryList>> {
        return flow{
            val remoteHistoryList = try {
                getRemoteHistory()
            } catch (e: Exception) {
                e.printStackTrace()
                println(tag + "getHistory: $e")
                null
            }

            remoteHistoryList?.let {
                dao.deleteAllHistory()
                dao.upsertHistoryList(remoteHistoryList.history?.map { it.toHistoryItemEntity() } ?: emptyList())
                emit(HistoryResult.Success(getLocalHistory()))
                return@flow
            }

            val localHistoryList = getLocalHistory()
            if(localHistoryList.history?.isNotEmpty() == true){
                emit(HistoryResult.Success(localHistoryList))
                return@flow
            }

            emit(HistoryResult.Error("Something went wrong"))
    }

    }
}