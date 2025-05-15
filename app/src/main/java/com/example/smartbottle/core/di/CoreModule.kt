package com.example.smartbottle.core.di

import com.example.smartbottle.core.data.CoreRepositoryImpl
import com.example.smartbottle.core.domain.CoreRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.endpoint
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module

val CoreModule = module {



    single {
        CoreRepositoryImpl(
            context = androidContext(),
            httpClient = get(),
            prefs = get(),
        )
    }.bind<CoreRepository>()

    single {
        HttpClient(CIO) {
            expectSuccess = true

            engine {
                endpoint {
                    connectTimeout = 5000
                    connectAttempts = 3
                    keepAliveTime = 5000
                }
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    })
            }

            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String){
                        println(message)
                    }
                }

                level = LogLevel.ALL
            }
        }
    }
}