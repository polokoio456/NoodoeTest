package com.nie.noodoetest.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nie.noodoetest.base.BaseViewModel
import com.nie.noodoetest.bean.Constant
import com.nie.noodoetest.data.remote.model.response.government.TransportationInformation
import com.nie.noodoetest.repository.login.LoginRepository
import com.nie.noodoetest.repository.modifytimezone.ModifyTimeZoneRepository
import com.nie.noodoetest.repository.transportationlist.TransportationListRepository
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo

class MainViewModel(
    private val loginRepository: LoginRepository,
    private val modifyTimeZoneRepository: ModifyTimeZoneRepository,
    private val transportationListRepository: TransportationListRepository
) : BaseViewModel() {

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> = _loginSuccess

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _timeZone = MutableLiveData<Int>()
    val timeZone: LiveData<Int> = _timeZone

    private val _modifyEmailSuccess = MutableLiveData<Boolean>()
    val modifyEmailSuccess: LiveData<Boolean> = _modifyEmailSuccess

    private val _signOutSuccess = MutableLiveData<Boolean>()
    val signOutSuccess: LiveData<Boolean> = _signOutSuccess

    private val _transportationInfo = MutableLiveData<TransportationInformation>()
    val transportationInfo: LiveData<TransportationInformation> = _transportationInfo

    fun login(userName: String, password: String) {
        loginRepository.login(userName, password)
            .flatMap { loginRepository.saveUserData(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showLoading() }
            .doFinally { hideLoading() }
            .ignoreElement()
            .subscribe({
                _loginSuccess.value = true
            }, {
                Log.e(Constant.TAG, it.stackTraceToString())
            }).addTo(compositeDisposable)
    }

    fun isLoggedIn() {
        loginRepository.isLoggedIn()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _loginSuccess.value = it
            }, {
                Log.e(Constant.TAG, it.stackTraceToString())
            }).addTo(compositeDisposable)
    }

    fun getEmail() {
        modifyTimeZoneRepository.getUserEmail()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _email.value = it
            }, {
                Log.e(Constant.TAG, it.stackTraceToString())
            }).addTo(compositeDisposable)
    }

    fun getTimeZone() {
        modifyTimeZoneRepository.getTimeZone()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _timeZone.value = it
            }, {
                Log.e(Constant.TAG, it.stackTraceToString())
            }).addTo(compositeDisposable)
    }

    fun modifyTimeZone(timeZone: Int) {
        modifyTimeZoneRepository.modifyRemoteTimeZone(timeZone)
            .andThen(Completable.defer { modifyTimeZoneRepository.modifyLocalTimeZone(timeZone) })
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showLoading() }
            .doFinally { hideLoading() }
            .subscribe({
                _modifyEmailSuccess.value = true
            }, {
                Log.e(Constant.TAG, it.stackTraceToString())
            }).addTo(compositeDisposable)
    }

    fun signOut() {
        modifyTimeZoneRepository.signOut()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _signOutSuccess.value = true
            }, {
                Log.e(Constant.TAG, it.stackTraceToString())
            }).addTo(compositeDisposable)
    }

    fun fetchTransportationInfo() {
        transportationListRepository.fetchTransportationInformation()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showLoading() }
            .doFinally { hideLoading() }
            .subscribe({
                _transportationInfo.value = it
            }, {
                Log.e(Constant.TAG, it.stackTraceToString())
            }).addTo(compositeDisposable)
    }
}