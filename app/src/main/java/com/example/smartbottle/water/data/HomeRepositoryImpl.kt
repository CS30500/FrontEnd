package com.example.smartbottle.water.data

import android.content.SharedPreferences
import com.example.smartbottle.core.data.NetworkConstants
import com.example.smartbottle.water.data.remote.DailyHydrationDto
import com.example.smartbottle.water.data.remote.toDailyHydration
import com.example.smartbottle.water.domain.DailyHydration
import com.example.smartbottle.water.domain.HomeRepository
import com.example.smartbottle.water.domain.HomeResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.coroutines.cancellation.CancellationException

class HomeRepositoryImpl (
    private val httpClient: HttpClient,
    private val prefs: SharedPreferences
) : HomeRepository {

    private val tag = "HomeRepository: "

    private val baseUrl = NetworkConstants.BASE_URL
    override suspend fun getDailyHydration(): Flow<HomeResult<DailyHydration>> {
        return flow{
            val remoteDailyHydration = try {
                getRemoteDailyHydration()
            } catch (e: Exception) {
                e.printStackTrace()
                if(e is CancellationException) throw e
                println(tag + "getDailyHydration: $e")
                null
            }

            remoteDailyHydration?.let {
                emit(HomeResult.Success(it))
            } ?: emit(HomeResult.Error("일일 기록을 가져올 수 없습니다."))
        }
    }

    private suspend fun getRemoteDailyHydration(): DailyHydration {
        val token = prefs.getString("jwt", null) ?: throw IllegalStateException("토큰이 없습니다.")

        val httpResponse = httpClient.get("$baseUrl/hydration/today") {
            header("Authorization", "Bearer $token")
        }

        println("$tag getRemoteDailyHydration status: ${httpResponse.status}")
        println("$tag getRemoteDailyHydration headers: ${httpResponse.headers}")

        val rawBody = httpResponse.bodyAsText()
        println("$tag getRemoteDailyHydration rawBody: $rawBody")

        val dailyHydrationDto = httpResponse.body<DailyHydrationDto>()

        return dailyHydrationDto.toDailyHydration()


    }

}