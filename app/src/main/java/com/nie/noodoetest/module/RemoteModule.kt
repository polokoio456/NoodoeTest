package com.nie.noodoetest.module

import com.nie.noodoetest.AppConfig
import com.nie.noodoetest.data.remote.NetworkService
import com.nie.noodoetest.data.remote.api.GovernmentApi
import com.nie.noodoetest.data.remote.api.WatchApi
import org.koin.dsl.module

val remoteModule = module {
    single { NetworkService(AppConfig.WATCH_END_POINT).create(WatchApi::class.java) }
    single { NetworkService(AppConfig.GOVERNMENT_END_POINT).create(GovernmentApi::class.java) }
}