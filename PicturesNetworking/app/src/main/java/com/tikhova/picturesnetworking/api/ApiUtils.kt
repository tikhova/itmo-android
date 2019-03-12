package com.tikhova.picturesnetworking.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiUtils {
    private const val BASE_URL = "https://api.unsplash.com/"
    private val mRetrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
    private val mClient = mRetrofit.create(UnsplashClient::class.java)

    fun getClient(): UnsplashClient {
        return mClient
    }
}
