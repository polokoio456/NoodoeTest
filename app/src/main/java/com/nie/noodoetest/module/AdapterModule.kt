package com.nie.noodoetest.module

import com.nie.noodoetest.ui.transportation.TransportationListAdapter
import org.koin.dsl.module

val adapterModule = module {
    factory { TransportationListAdapter() }
}