package com.nie.noodoetest

import com.nie.noodoetest.data.remote.model.response.watch.LoginResponse
import com.nie.noodoetest.repository.login.LoginRepository
import com.nie.noodoetest.repository.modifytimezone.ModifyTimeZoneRepository
import com.nie.noodoetest.repository.transportationlist.TransportationListRepository
import com.nie.noodoetest.ui.login.MainViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class MainViewModelTest {
    private lateinit var viewModel: MainViewModel

    private val loginRepository = mockk<LoginRepository>(relaxed = true)
    private val modifyTimeZoneRepository = mockk<ModifyTimeZoneRepository>(relaxed = true)
    private val transportationListRepository = mockk<TransportationListRepository>(relaxed = true)

    private val loginResponse = LoginResponse(
        objectId = "",
        username = "",
        code = "",
        isVerifiedReportEmail = true,
        reportEmail = "",
        createdAt = "",
        updatedAt = "",
        timezone = 0,
        parameter = 0,
        number = 0,
        phone = "",
        sessionToken = ""
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = MainViewModel(loginRepository, modifyTimeZoneRepository, transportationListRepository)
    }

    @Test
    fun login() {
        every { loginRepository.login(any(), any()) } returns Single.just(loginResponse)
        every { loginRepository.saveUserData(any()) } returns Single.just(true)

        viewModel.login("", "")

        verifyOrder {
            loginRepository.login(any(), any())
            loginRepository.saveUserData(any())
        }
    }

    @Test
    fun modifyTimeZone() {
        every { modifyTimeZoneRepository.modifyRemoteTimeZone(any()) } returns Completable.complete()
        every { modifyTimeZoneRepository.modifyLocalTimeZone(any()) } returns Completable.complete()

        viewModel.modifyTimeZone(1)

        verifyOrder {
            modifyTimeZoneRepository.modifyRemoteTimeZone(any())
            modifyTimeZoneRepository.modifyLocalTimeZone(any())
        }
    }
}