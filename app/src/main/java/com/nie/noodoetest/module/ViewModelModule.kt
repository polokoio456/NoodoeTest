package com.nie.noodoetest.module

import com.nie.noodoetest.ui.login.LoginViewModel
import com.nie.noodoetest.ui.modifytimezone.ModifyTimezoneViewModel
import com.nie.noodoetest.ui.transportation.TransportationListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { TransportationListViewModel(get()) }
    viewModel { ModifyTimezoneViewModel() }
}