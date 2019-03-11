package com.tikhova.picturesnetworking.api

import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

object ApiUtils {
    private const val BASE_URL = "https://api.unsplash.com/"
    private val mRetrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val mClient =  mRetrofit.create(UnsplashClient::class.java)

    fun getClient() : UnsplashClient {
        return mClient
    }
}
