package com.example.randomuser

import android.app.Application
import com.example.randomuser.di.dbModule
import com.example.randomuser.di.networkModule
import com.example.randomuser.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class BaseApp : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@BaseApp)
            modules(listOf(dbModule, networkModule, viewModelModule))
        }
    }
}