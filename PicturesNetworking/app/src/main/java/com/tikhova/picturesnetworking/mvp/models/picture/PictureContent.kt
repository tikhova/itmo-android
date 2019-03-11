package com.tikhova.picturesnetworking.mvp.models.picture

import com.tikhova.picturesnetworking.api.ApiUtils
import com.tikhova.picturesnetworking.ui.adapters.RecyclerViewAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

object PictureContent {

    val ITEMS: MutableList<Picture> = ArrayList()
    val ITEM_MAP: MutableMap<String?, Picture> = HashMap()
    var COUNT = 0

    private fun addItem(item: Picture) {
        ITEMS.add(item)
        ITEM_MAP[item.id] = item
    }

    fun getList(query: String, count: Int) {
        val call = ApiUtils.getClient().getPictures(query, count)
        call.enqueue(object : Callback<PicturesWrapper> {
            override fun onResponse(call: Call<PicturesWrapper>, response: Response<PicturesWrapper>) {
                ITEMS.clear()
                ITEM_MAP.clear()
                var list = response.body()!!.results
                list?.let {
                    for (item in list) {
                        addItem(item)
                    }
                }
                COUNT = ITEMS.size
            }

            override fun onFailure(call: Call<PicturesWrapper>, t: Throwable) {}
        })
    }
}