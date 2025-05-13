package com.example.smartbottle.auth.di
import com.example.smartbottle.auth.presentation.login.LoginViewModel
import com.example.smartbottle.auth.presentation.signup.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AuthModule = module{
    viewModel { LoginViewModel() }
    viewModel { RegisterViewModel() }
}