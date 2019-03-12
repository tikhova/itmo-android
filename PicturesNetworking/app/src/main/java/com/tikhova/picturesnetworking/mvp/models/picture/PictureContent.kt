package com.tikhova.picturesnetworking.mvp.models.picture

import com.tikhova.picturesnetworking.api.ApiUtils
import java.util.*

object PictureContent {

    val ITEMS: MutableList<Picture> = ArrayList()
    val ITEM_MAP: MutableMap<String?, Picture> = HashMap()
    var COUNT = 0

    private fun addItem(item: Picture) {
        ITEMS.add(item)
        ITEM_MAP[item.id] = item
    }

    suspend fun getList(query: String, count: Int) {
        val call = ApiUtils.getClient().getPictures(query, count).await()
        if (call.isSuccessful) {
            ITEMS.clear()
            ITEM_MAP.clear()
            var list = call.body()!!.results
            list?.let {
                for (item in list) {
                    addItem(item)
                }
            }
            COUNT = ITEMS.size
        }
    }
}