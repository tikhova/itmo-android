package com.tikhova.picturesnetworking.picture

import com.tikhova.picturesnetworking.PictureListActivity
import com.tikhova.picturesnetworking.RecyclerViewAdapter
import com.tikhova.picturesnetworking.UnsplashClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


object PictureContent {

    val ITEMS: MutableList<Picture> = ArrayList()
    val ITEM_MAP: MutableMap<String?, Picture> = HashMap()
    lateinit var ADAPTER: RecyclerViewAdapter

    var COUNT = 0
    val BASE_URL = "https://api.unsplash.com/"
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val client = retrofit.create(UnsplashClient::class.java)

    private fun addItem(item: Picture) {
        ITEMS.add(item)
        ITEM_MAP[item.id] = item
    }

    fun getList(query: String, count: Int) {
        val call = client.getPictures(query, count)
        call.enqueue(object : Callback<PicturesWrapper> {
            override fun onResponse(call: Call<PicturesWrapper>, response: Response<PicturesWrapper>) {
                ITEMS.clear()
                ITEM_MAP.clear()
                var list = response.body()!!.results
                list?.let{
                    for (item in list) {
                        addItem(item)
                    }
                }
                COUNT = ITEMS.size
                ADAPTER.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<PicturesWrapper>, t: Throwable) {}
        })
    }
}