package com.nie.noodoetest.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nie.noodoetest.base.BaseViewModel
import com.nie.noodoetest.bean.Constant
import com.nie.noodoetest.repository.login.LoginRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo

class LoginViewModel(private val loginRepository: LoginRepository) : BaseViewModel() {

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> = _loginSuccess

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
}