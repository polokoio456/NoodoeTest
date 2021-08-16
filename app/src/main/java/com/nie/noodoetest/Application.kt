package com.nie.noodoetest

import android.app.Application
import com.nie.noodoetest.module.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Application)
            modules(
                listOf(
                    utilModule,
                    adapterModule,
                    repositoryModule,
                    viewModelModule,
                    remoteModule
                )
            )
        }
    }
}