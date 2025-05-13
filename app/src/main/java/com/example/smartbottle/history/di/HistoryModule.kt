import androidx.room.Room
import com.example.smartbottle.history.data.HistoryRepositoryImpl
import com.example.smartbottle.history.data.local.HistoryDatabase
import com.example.smartbottle.history.domain.HistoryRepository
import com.example.smartbottle.history.presentation.HistoryViewModel
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

}