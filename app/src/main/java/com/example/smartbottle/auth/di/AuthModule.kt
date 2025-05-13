package com.example.smartbottle.auth.di
import com.example.smartbottle.auth.data.AuthRepositoryImpl
import com.example.smartbottle.auth.domain.AuthRepository
import com.example.smartbottle.auth.presentation.login.LoginViewModel
import com.example.smartbottle.auth.presentation.signup.RegisterViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val AuthModule = module{
    // SharedPreferences 의존성 주입
    single {
        androidContext().getSharedPreferences("auth_prefs", android.content.Context.MODE_PRIVATE)
    }

    // AuthRepository 주입
    single {
        AuthRepositoryImpl(
            httpClient = get(),
            prefs = get() // 위에서 주입한 SharedPreferences
        )
    }.bind<AuthRepository>()

    // ViewModel 의존성 주입 예시
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }


}