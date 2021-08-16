package com.nie.noodoetest.data.remote

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.nie.noodoetest.AppConfig
import com.nie.noodoetest.data.remote.model.response.watch.LoginResponse
import com.nie.noodoetest.extension.toObject
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkService(private val baseUrl: String) : KoinComponent {
    companion object {
        private const val TIMEOUT_SECOND = 60L

        private const val HEADER_APPLICATION_ID = "X-Parse-Application-Id"
        private const val HEADER_SESSION_TOKEN = "X-Parse-Session-Token"
    }

    private val preferences by inject<NoodoeSharedPreferences>()

    private val retrofit: Retrofit by lazy { createRetrofit() }

    private fun createRetrofit(): Retrofit {
        val gson = GsonBuilder()
            .setPrettyPrinting()
            .create()

        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(baseUrl)
            .client(createClient())
            .build()
    }

    private fun createClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_SECOND, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECOND, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECOND, TimeUnit.SECONDS)
            .addInterceptor { chain -> httpRequestInterceptor(chain) }
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    private fun httpRequestInterceptor(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().apply {
            addHeader(HEADER_APPLICATION_ID, AppConfig.APPLICATION_ID)

            if (preferences.userData.isNotEmpty()) {
                val sessionToken = preferences.userData.toObject(LoginResponse::class.java).sessionToken
                addHeader(HEADER_SESSION_TOKEN, sessionToken)
            }
        }.build()

        return chain.proceed(request)
    }

    fun <T> create(clazz: Class<T>): T = retrofit.create(clazz)
}