package com.example.smartbottle.profile.data

import android.content.SharedPreferences
import com.example.smartbottle.core.data.NetworkConstants
import com.example.smartbottle.profile.data.remote.ProfileDto
import com.example.smartbottle.profile.data.remote.toProfile
import com.example.smartbottle.profile.domain.Profile
import com.example.smartbottle.profile.domain.ProfileRepository
import com.example.smartbottle.profile.domain.ProfileResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.coroutines.cancellation.CancellationException

class ProfileRepositoryImpl(
    private val httpClient: HttpClient,
    private val prefs: SharedPreferences
) : ProfileRepository {
    private val tag = "ProfileRepository: "

    private val baseUrl = NetworkConstants.BASE_URL

    override suspend fun getProfile(): Flow<ProfileResult<Profile>> {
        return flow{
            val remoteProfile = try {
                getRemoteProfile()
            } catch (e: Exception) {
                e.printStackTrace()
                if(e is CancellationException) throw e
                println(tag + "getProfile: $e")
                null
            }

            // remoteProfile이 있으면 성공, 없으면 에러
            remoteProfile?.let { profile ->
                emit(ProfileResult.Success(profile))
            } ?: emit(ProfileResult.Error("프로필 정보를 가져올 수 없습니다."))

        }
    }

    private suspend fun getRemoteProfile(): Profile {
        val token = prefs.getString("jwt", null) ?: throw IllegalStateException("토큰이 없습니다.")

        val httpResponse = httpClient.get("$baseUrl/profile") {
            header("Authorization", "Bearer $token")
        }

        println("$tag getRemoteProfile status: ${httpResponse.status}")
        println("$tag getRemoteProfile headers: ${httpResponse.headers}")

        val rawBody = httpResponse.bodyAsText()
        println("$tag getRemoteProfile rawBody: $rawBody")

        val profileDto = httpResponse.body<ProfileDto>()

        return profileDto.toProfile()

    }

    override suspend fun updateProfile(profile: Profile): Result<Unit> {
        return try {
            val token = prefs.getString("jwt", null) ?: throw IllegalStateException("토큰이 없습니다.")

            httpClient.put("$baseUrl/profile") {
                header("Authorization", "Bearer $token")
                setBody(profile.toDto()) // convert Profile to ProfileDto
            }

            Result.success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            Result.failure(e)
        }
    }



}

fun Profile.toDto(): ProfileDto {
    return ProfileDto(
        age = age,
        height = height,
        sex = sex,
        user_id = user_id,
        weight = weight,
        totalDays = totalDays,
        longestStreak = longestStreak,
        hydration = hydration,
        alertTemperature = alertTemperature,
        hydrationReminder = hydrationReminder,
        dndStart = dndStart,
        dndEnd = dndEnd
    )
}