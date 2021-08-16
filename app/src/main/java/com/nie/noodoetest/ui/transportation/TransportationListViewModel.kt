package com.nie.noodoetest.ui.transportation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nie.noodoetest.base.BaseViewModel
import com.nie.noodoetest.bean.Constant
import com.nie.noodoetest.data.remote.model.response.government.TransportationInformation
import com.nie.noodoetest.repository.transportationlist.TransportationListRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo

class TransportationListViewModel(private val transportationListRepository: TransportationListRepository) : BaseViewModel() {

    private val _transportationInfo = MutableLiveData<TransportationInformation>()
    val transportationInfo: LiveData<TransportationInformation> = _transportationInfo

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