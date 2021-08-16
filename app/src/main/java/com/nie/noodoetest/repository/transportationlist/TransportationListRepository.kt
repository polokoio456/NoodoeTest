package com.nie.noodoetest.repository.transportationlist

import com.nie.noodoetest.data.remote.model.response.government.TransportationInformation
import io.reactivex.Single

interface TransportationListRepository {
    fun fetchTransportationInformation(): Single<TransportationInformation>
}