package com.nie.noodoetest.repository.login

import com.nie.noodoetest.data.remote.NoodoeSharedPreferences
import com.nie.noodoetest.data.remote.api.WatchApi
import com.nie.noodoetest.data.remote.model.response.watch.LoginResponse
import com.nie.noodoetest.extension.toJson
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class LoginRepositoryImpl(
    private val watchApi: WatchApi,
    private val preferences: NoodoeSharedPreferences
) : LoginRepository {

    override fun login(userName: String, password: String): Single<LoginResponse> {
        return watchApi.login(userName, password)
            .subscribeOn(Schedulers.io())
    }

    override fun saveUserData(loginResponse: LoginResponse): Single<Boolean> {
        return Single.create<Boolean> {
            preferences.userData = loginResponse.toJson()
            it.onSuccess(true)
        }.subscribeOn(Schedulers.io())
    }

    override fun isLoggedIn(): Single<Boolean> {
        return Single.just(preferences.userData.isNotEmpty())
            .subscribeOn(Schedulers.io())
    }
}