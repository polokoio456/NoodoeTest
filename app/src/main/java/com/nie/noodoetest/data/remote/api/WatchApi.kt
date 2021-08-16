package com.nie.noodoetest.data.remote.api

import com.nie.noodoetest.data.remote.model.request.UpdateTimeZone
import com.nie.noodoetest.data.remote.model.response.watch.LoginResponse
import com.nie.noodoetest.data.remote.model.response.watch.UpdateTimezoneResponse
import io.reactivex.Single
import retrofit2.http.*

interface WatchApi {
    @FormUrlEncoded
    @POST("api/login")
    fun login(@Field("username") userName: String, @Field("password") password: String): Single<LoginResponse>

    @PUT("api/users/{object_id}")
    fun updateTimezone(@Path("object_id") objectId: String, @Body timeZone: UpdateTimeZone): Single<UpdateTimezoneResponse>
}