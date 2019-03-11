package com.tikhova.picturesnetworking.api

import com.tikhova.picturesnetworking.mvp.models.picture.PicturesWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashClient {

    @GET(
        "/search/photos?client_id=c68864954c125963dddd4ff7fa8288839b3db8d8dabcfc74939229db8932c193" +
                "&page=1"
    )
    fun getPictures(@Query("query") query: String, @Query("per_page") count: Int): Call<PicturesWrapper>
}