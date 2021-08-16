package com.nie.noodoetest.repository.modifytimezone

import com.nie.noodoetest.data.remote.NoodoeSharedPreferences
import com.nie.noodoetest.data.remote.api.WatchApi
import com.nie.noodoetest.data.remote.model.request.UpdateTimeZone
import com.nie.noodoetest.data.remote.model.response.watch.LoginResponse
import com.nie.noodoetest.extension.toJson
import com.nie.noodoetest.extension.toObject
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class ModifyTimeZoneRepositoryImpl(
    private val preferences: NoodoeSharedPreferences,
    private val watchApi: WatchApi
) : ModifyTimeZoneRepository {

    override fun getUserEmail(): Single<String> {
        return Single.create<String> {
            val loginResponse = preferences.userData.toObject(LoginResponse::class.java)
            it.onSuccess(loginResponse.username)
        }.subscribeOn(Schedulers.io())
    }

    override fun getTimeZone(): Single<Int> {
        return Single.create<Int> {
            val loginResponse = preferences.userData.toObject(LoginResponse::class.java)
            it.onSuccess(loginResponse.timezone)
        }.subscribeOn(Schedulers.io())
    }

    override fun modifyRemoteTimeZone(timeZone: Int): Completable {
        val loginResponse = preferences.userData.toObject(LoginResponse::class.java)
        return watchApi.updateTimezone(loginResponse.objectId, UpdateTimeZone(timeZone))
            .ignoreElement()
            .subscribeOn(Schedulers.io())
    }

    override fun modifyLocalTimeZone(timeZone: Int): Completable {
        return Completable.fromAction {
            val loginResponse = preferences.userData.toObject(LoginResponse::class.java)
            preferences.userData = loginResponse.copy(timezone = timeZone).toJson()
        }.subscribeOn(Schedulers.io())
    }

    override fun signOut(): Completable {
        return Completable.fromAction {
            preferences.userData = ""
        }.subscribeOn(Schedulers.io())
    }
}