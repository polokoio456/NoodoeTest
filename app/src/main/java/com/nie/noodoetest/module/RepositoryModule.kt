package com.nie.noodoetest.module

import com.nie.noodoetest.repository.login.LoginRepository
import com.nie.noodoetest.repository.login.LoginRepositoryImpl
import com.nie.noodoetest.repository.transportationlist.TransportationListRepository
import com.nie.noodoetest.repository.transportationlist.TransportationListRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<LoginRepository> { LoginRepositoryImpl(get(), get()) }
    single<TransportationListRepository> { TransportationListRepositoryImpl(get()) }
}