package com.nie.noodoetest.repository.modifytimezone

import io.reactivex.Completable
import io.reactivex.Single

interface ModifyTimeZoneRepository {
    fun getUserEmail(): Single<String>
    fun getTimeZone(): Single<Int>
    fun modifyRemoteTimeZone(timeZone: Int): Completable
    fun modifyLocalTimeZone(timeZone: Int): Completable
    fun signOut(): Completable
}