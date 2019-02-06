package com.tikhova.picturesnetworking.dummy

import android.graphics.Bitmap
import android.os.AsyncTask
import com.tikhova.picturesnetworking.PictureListActivity
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

object PictureContent {

    val ITEMS: MutableList<PictureItem> = ArrayList()
    val ITEM_MAP: MutableMap<String, PictureItem> = HashMap()
    lateinit var ADAPTER: PictureListActivity.SimpleItemRecyclerViewAdapter

    private const val COUNT = 25
    private const val CLIENT_ID = "c68864954c125963dddd4ff7fa8288839b3db8d8dabcfc74939229db8932c193"
    const val QUERY = "sky"

    init {
        RequestJSONObjectAsyncTask().execute(
            "https://api.unsplash.com/search/photos?client_id=" +
                    CLIENT_ID + "&page=1&per_page=" + COUNT + "&query=" + QUERY
        )
    }

    private fun addItem(item: PictureItem) {
        ITEMS.add(item)
        ITEM_MAP[item.id] = item
    }

    private fun createPictureItem(json: JSONObject): PictureItem {
        val urls = json.getJSONObject("urls")
        return PictureItem(
            json.getString("id"), json.getString("description"),
            urls.getString("thumb"), urls.getString("regular")
        )
    }

    data class PictureItem(val id: String, val description: String, val thumbnailURL: String, val fullURL: String) {
        var thumb: Bitmap? = null

        override fun toString(): String = description
    }

    class RequestJSONObjectAsyncTask : AsyncTask<String, Unit, JSONObject>() {
        override fun doInBackground(vararg urlString: String): JSONObject {
            val url = URL(urlString[0])
            val urlConnection = url.openConnection() as HttpURLConnection
            return JSONObject(urlConnection.inputStream.bufferedReader().readText())
        }

        override fun onPostExecute(json: JSONObject?) {
            val jsonPictures = json!!.getJSONArray("results")
            for (i in 0 until COUNT - 1) {
                addItem(createPictureItem(jsonPictures.getJSONObject(i)))
            }
            ADAPTER.notifyDataSetChanged()
        }
    }
}