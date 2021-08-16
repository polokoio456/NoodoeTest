package com.nie.noodoetest.module

import com.nie.noodoetest.data.remote.NoodoeSharedPreferences
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val utilModule = module {
    single { NoodoeSharedPreferences(androidApplication()) }
}