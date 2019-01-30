package com.tikhova.picturesnetworking.dummy

import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.provider.MediaStore
import android.util.Log
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.InputStream
import java.util.ArrayList
import java.util.HashMap
import java.net.HttpURLConnection
import java.net.URL

object PictureContent {

    val ITEMS: MutableList<PictureItem> = ArrayList()
    val ITEM_MAP: MutableMap<String, PictureItem> = HashMap()

    private val COUNT = 25
    private val QUERY = "sky"
    private val CLIENT_ID = "c68864954c125963dddd4ff7fa8288839b3db8d8dabcfc74939229db8932c193"

    init {
        val requestResult = requestJSONObjectAsyncTask().execute("https://api.unsplash.com/search/photos?client_id=" +
                CLIENT_ID + "&page=1&per_page=" + COUNT + "&query=" + QUERY).get()
        val jsonPictures = requestResult.getJSONArray("results")
        for (i in 0..COUNT - 1) {
            addItem(createPictureItem(jsonPictures.getJSONObject(i)))
        }
    }

    private fun addItem(item: PictureItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

    private fun createPictureItem(json: JSONObject): PictureItem {
        val urls = json.getJSONObject("urls")
        return PictureItem(
            json.getString("id"), json.getString("description"),
            urls.getString("thumb"), urls.getString("regular")        )
    }

    data class PictureItem(val id: String, val description: String, val thumbnailURL: String, val fullURL: String) {
        override fun toString(): String = description
    }

    class requestJSONObjectAsyncTask: AsyncTask<String, Unit, JSONObject>() {
        override fun doInBackground(vararg urlString: String): JSONObject {
            val url = URL(urlString[0])
            val urlConnection = url.openConnection() as HttpURLConnection
            val json = urlConnection.inputStream.bufferedReader().readText()
            return JSONObject(json)
        }
    }
}