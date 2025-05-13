package com.example.smartbottle.auth.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.smartbottle.auth.data.remote.TokenResponse
import com.example.smartbottle.auth.domain.AuthRepository
import com.example.smartbottle.auth.domain.AuthResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val prefs: SharedPreferences
) : AuthRepository {

    private val baseUrl = "http://192.168.0.8:8000"

    override suspend fun login(email: String, password: String): AuthResult<Unit>{
        return try {
            val response = httpClient.post("$baseUrl/auth/login") {
                contentType(io.ktor.http.ContentType.Application.Json)
                setBody(
                    mapOf(
                        "user_id" to email,
                        "password" to password
                    )
                )
            }

            if (response.status.value in 200..299) {

                val tokenResponse = response.body<TokenResponse>()

                prefs.edit() {
                    putString("jwt", tokenResponse.accessToken)
                }

                AuthResult.Authorized(Unit)
            } else {
                AuthResult.UnknownError()
            }

        } catch (e: ClientRequestException) {
            // 4xx 요청 오류
            when (e.response.status) {
                HttpStatusCode.Unauthorized -> AuthResult.Unauthorized()
                else -> AuthResult.UnknownError()
            }
        } catch (e: ServerResponseException) {
            // 5xx 서버 오류
            AuthResult.UnknownError()
        } catch (e: RedirectResponseException) {
            // 3xx 리다이렉트 오류
            AuthResult.UnknownError()
        } catch (e: Exception) {
            // 기타 예외
            AuthResult.UnknownError()
        }

    }

    override suspend fun register(email: String, password: String, checkPassword: String): AuthResult<Unit>{
        return try {
            val response = httpClient.post("$baseUrl/auth/register") {
                contentType(io.ktor.http.ContentType.Application.Json)
                setBody(
                    mapOf(
                        "user_id" to email,
                        "password" to password,
                        "password_check" to checkPassword
                    )
                )
            }

            if (response.status.value in 200..299) {
                AuthResult.Authorized(Unit)
            } else {
                AuthResult.UnknownError()
            }

        } catch (e: ClientRequestException) {
            // 4xx 응답에 대한 예외
            when (e.response.status) {
                HttpStatusCode.Unauthorized -> AuthResult.Unauthorized()
                else -> AuthResult.UnknownError()
            }
        } catch (e: ServerResponseException) {
            // 5xx 응답에 대한 예외
            AuthResult.UnknownError()
        } catch (e: RedirectResponseException) {
            // 3xx 응답에 대한 예외
            AuthResult.UnknownError()
        } catch (e: Exception) {
            // 기타 예외
            AuthResult.UnknownError()
        }

    }

    override suspend fun authenticate(): AuthResult<Unit> {
        return try {
            // SharedPreferences에서 JWT 토큰을 꺼냄
            val token = prefs.getString("jwt", null) ?: return AuthResult.Unauthorized()

            // 서버에 토큰을 포함해 인증 요청
            val response = httpClient.post("$baseUrl/auth/authenticate") {
                header("Authorization", "Bearer $token")
            }

            // 2xx 응답이면 인증 성공
            if (response.status.value in 200..299) {
                AuthResult.Authorized(Unit)
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: ClientRequestException) {
            // 4xx 요청 오류
            when (e.response.status) {
                HttpStatusCode.Unauthorized -> AuthResult.Unauthorized()
                else -> AuthResult.UnknownError()
            }
        } catch (e: ServerResponseException) {
            // 5xx 서버 오류
            AuthResult.UnknownError()
        } catch (e: RedirectResponseException) {
            // 3xx 리다이렉트 오류
            AuthResult.UnknownError()
        } catch (e: Exception) {
            // 기타 예외
            AuthResult.UnknownError()
        }

    }

}