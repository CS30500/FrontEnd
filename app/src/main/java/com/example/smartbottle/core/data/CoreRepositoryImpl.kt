package com.example.smartbottle.core.data

import android.content.SharedPreferences
import android.util.Log
import com.example.smartbottle.auth.domain.AuthResult
import com.example.smartbottle.core.data.remote.BleManager
import com.example.smartbottle.core.domain.CoreRepository
import com.example.smartbottle.core.domain.CoreResult
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CoreRepositoryImpl(
    private val bleManager: BleManager,
    private val httpClient: HttpClient,
    private val prefs: SharedPreferences
) : CoreRepository {

    private val tag = "CoreRepository: "
    private val baseUrl = NetworkConstants.BASE_URL

    override suspend fun postBleData (
        temperature: Float,
        pressure: Int,
        waterIntake: Float
    ) : CoreResult<Unit> {
        return try {
                // 예: /bottle/data (서버에 실제로 해당 엔드포인트가 존재해야 함)
                val token = prefs.getString("jwt", null) ?: return CoreResult.Error("토큰이 없습니다.")

                val response: HttpResponse = httpClient.post("$baseUrl/bottle/data") {
                    header("Authorization", "Bearer $token")
                    contentType(ContentType.Application.Json)
                    // 만드는 JSON 구조는 서버 요구사항에 따라 맞춤 구성 필요
                    setBody(
                        mapOf(
                            "temperature" to temperature,
                            "pressure" to pressure,
                            "waterIntake" to waterIntake
                        )
                    )
                }

                if (response.status.isSuccess()) {
                    CoreResult.Success(Unit)
                } else {
                    CoreResult.Error("error")
                }

            }  catch (e: ClientRequestException) {
            // 4xx 요청 오류
            when (e.response.status) {
                HttpStatusCode.Unauthorized -> CoreResult.Error("error")
                else -> CoreResult.Error("error")
            }
        } catch (e: ServerResponseException) {
            // 5xx 서버 오류
            CoreResult.Error("error")
        } catch (e: RedirectResponseException) {
            // 3xx 리다이렉트 오류
            CoreResult.Error("error")
        } catch (e: Exception) {
            // 기타 예외
            CoreResult.Error("error")
        }

    }

}