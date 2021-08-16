package com.nie.noodoetest.repository.login

import com.nie.noodoetest.data.remote.model.response.watch.LoginResponse
import io.reactivex.Single

interface LoginRepository {
    fun login(userName: String, password: String): Single<LoginResponse>
    fun saveUserData(loginResponse: LoginResponse): Single<Boolean>
    fun isLoggedIn(): Single<Boolean>
}