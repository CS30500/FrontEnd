package com.example.smartbottle.auth.domain

interface AuthRepository {
    suspend fun register(email: String, password: String, checkPassword: String): AuthResult<Unit>
    suspend fun login(email: String, password: String): AuthResult<Unit>
    suspend fun authenticate(): AuthResult<Unit>

}