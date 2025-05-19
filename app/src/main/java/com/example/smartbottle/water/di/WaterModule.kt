package com.example.smartbottle.water.di
import com.example.smartbottle.water.data.HomeRepositoryImpl
import com.example.smartbottle.water.domain.HomeRepository
import com.example.smartbottle.water.presentation.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val WaterModule = module {
    viewModel{HomeViewModel(get())}

    single {
        HomeRepositoryImpl(
            httpClient = get(),
            prefs = get()
        )
    }.bind<HomeRepository>()
}