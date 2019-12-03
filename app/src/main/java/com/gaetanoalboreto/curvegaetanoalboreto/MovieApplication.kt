package com.gaetanoalboreto.curvegaetanoalboreto

import android.app.Application
import com.gaetanoalboreto.curvegaetanoalboreto.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {

            androidContext(applicationContext)

            modules(appModule)
        }
    }
}