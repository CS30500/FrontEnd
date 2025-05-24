package com.example.smartbottle.history.data

import android.content.SharedPreferences
import android.util.Log
import com.example.smartbottle.core.data.NetworkConstants
import com.example.smartbottle.history.data.local.HistoryDao
import com.example.smartbottle.history.data.remote.HistoryItemDto
import com.example.smartbottle.history.data.remote.HistoryListDto
import com.example.smartbottle.history.domain.HistoryList
import com.example.smartbottle.history.domain.HistoryRepository
import com.example.smartbottle.history.domain.HistoryResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.coroutines.cancellation.CancellationException

class HistoryRepositoryImpl(
    private val httpClient: HttpClient,
    private val dao: HistoryDao,
    private val prefs: SharedPreferences
) : HistoryRepository {

    private val tag = "HistoryRepository: "

    private val baseUrl = NetworkConstants.BASE_URL

    private fun formatMonth(month: Int): String {
        return month.toString().padStart(2, '0')
    }


    private suspend fun getLocalHistory(year: Int, month: Int): HistoryList{

        val formattedMonth = formatMonth(month)
        Log.d(tag ,"getLocalHistory: $year-$formattedMonth")

        val localHistory = dao.getHistoryByYearMonth(year.toString(), formattedMonth)
        Log.d(tag ,"getLocalHistory: $localHistory")

        val historyList = HistoryList(
            history = localHistory.map { it.toHistoryItem() }
        )

        return historyList
    }

    private suspend fun getRemoteHistory(year : Int, month : Int): HistoryList {
        // 로그인 시 저장된 JWT 토큰을 가져옴
        val token = prefs.getString("jwt", null) ?: throw IllegalStateException("토큰이 없습니다.")

        // 1) 서버 요청 후, HttpResponse를 통째로 받아 세부 정보 확인
        val httpResponse = httpClient.get("$baseUrl/hydration/monthly") {
            header("Authorization", "Bearer $token")
            parameter("year", year)
            parameter("month", month)
        }

        // 2) 상태 코드, 헤더 로그로 확인 (서버 상태가 200인지, 권한 에러가 아닌지 점검)
        println("$tag getRemoteHistory status: ${httpResponse.status}")
        println("$tag getRemoteHistory headers: ${httpResponse.headers}")

        // 3) 실제 응답의 원본 문자열(JSON 등)를 한 번 출력해보기
        val rawBody = httpResponse.bodyAsText()
        println("$tag getRemoteHistory rawBody: $rawBody")

        // 4) 필요한 DTO 형태로 파싱 (List<HistoryItemDto>)
        val dtoList: List<HistoryItemDto> = httpResponse.body()

        // 5) HistoryListDto(내부에 history 필드)를 만들어서 최종 변환
        val historyListDto = HistoryListDto(history = dtoList)

        // 6) toHistoryList()로 도메인 모델(HistoryList)로 변환
        return historyListDto.toHistoryList()
    }


    override suspend fun getHistory(year : Int, month : Int): Flow<HistoryResult<HistoryList>> {
        return flow{
            val remoteHistoryList = try {
                getRemoteHistory(year = year, month = month)
            } catch (e: Exception) {
                e.printStackTrace()
                if(e is CancellationException) throw e
                println(tag + "getHistory: $e")
                null
            }

            remoteHistoryList?.let {
                dao.deleteAllHistory()
                dao.upsertHistoryList(remoteHistoryList.history?.map { it.toHistoryItemEntity() } ?: emptyList())
                emit(HistoryResult.Success(getLocalHistory(year, month)))
                return@flow
            }

            val localHistoryList = getLocalHistory(year, month)
            if(localHistoryList.history?.isNotEmpty() == true){
                emit(HistoryResult.Success(localHistoryList))
                return@flow
            }

            emit(HistoryResult.Error("Something went wrong"))
    }

    }
}