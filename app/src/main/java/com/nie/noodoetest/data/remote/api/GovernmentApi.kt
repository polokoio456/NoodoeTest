package com.nie.noodoetest.data.remote.api

import com.nie.noodoetest.data.remote.model.response.government.TransportationInformation
import io.reactivex.Single
import retrofit2.http.GET

interface GovernmentApi {
    @GET("dotapp/news.json")
    fun fetchTransportationInformation(): Single<TransportationInformation>
}