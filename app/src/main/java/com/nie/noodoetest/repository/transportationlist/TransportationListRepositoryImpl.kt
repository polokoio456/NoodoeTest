package com.nie.noodoetest.repository.transportationlist

import com.nie.noodoetest.data.remote.api.GovernmentApi
import com.nie.noodoetest.data.remote.model.response.government.TransportationInformation
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class TransportationListRepositoryImpl(private val governmentApi: GovernmentApi) : TransportationListRepository {

    override fun fetchTransportationInformation(): Single<TransportationInformation> {
        return governmentApi.fetchTransportationInformation()
            .subscribeOn(Schedulers.io())
    }
}