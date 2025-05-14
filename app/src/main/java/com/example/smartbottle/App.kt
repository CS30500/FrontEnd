package com.example.smartbottle

import HistoryModule
import android.app.Application
import com.example.smartbottle.auth.di.AuthModule
import com.example.smartbottle.core.di.CoreModule
import com.example.smartbottle.profile.di.ProfileModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                CoreModule,
                AuthModule,
                HistoryModule,
                ProfileModule
            )
        }
    }

}