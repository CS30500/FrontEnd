package com.example.smartbottle.profile.di

import com.example.smartbottle.history.presentation.HistoryViewModel
import com.example.smartbottle.profile.data.ProfileRepositoryImpl
import com.example.smartbottle.profile.domain.ProfileRepository
import com.example.smartbottle.profile.presentation.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val ProfileModule = module {

    viewModel{ ProfileViewModel(get()) }

    single {
        ProfileRepositoryImpl(
            httpClient = get(),
            prefs = get()
        )
    }.bind<ProfileRepository>()

}