import androidx.room.Room
import com.example.smartbottle.history.data.HistoryRepositoryImpl
import com.example.smartbottle.history.data.local.HistoryDatabase
import com.example.smartbottle.history.domain.HistoryRepository
import com.example.smartbottle.history.presentation.HistoryViewModel
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
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val HistoryModule = module {

    viewModel{ HistoryViewModel(get())}

    single {
        HistoryRepositoryImpl(
            httpClient = get(),
            dao = get()
        )
    }.bind<HistoryRepository>()

    single {
        Room.databaseBuilder(
            androidApplication(),
            HistoryDatabase::class.java,
            "history.db"
        ).build()
    }

    single {
        get<HistoryDatabase>().dao
    }

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