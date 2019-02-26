package com.tikhova.picturesnetworking

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.ResultReceiver
import android.util.Log
import com.tikhova.picturesnetworking.picture.PictureContent
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class PictureLoaderService : IntentService("PictureLoader") {
    val DOWNLOAD_CANCELLED = 1
    val DOWNLOAD_SUCCESS = 2
    val DOWNLOAD_ERROR = 3

    companion object {
        fun load(context: Context, url: String?, id: String?, imageReceiver: ResultReceiver, index: Int = -1) {
            val intent = Intent(context, PictureLoaderService::class.java)
            intent.putExtra("url", url)
            intent.putExtra("id", id)
            intent.putExtra("receiver", imageReceiver)
            intent.putExtra("index", index)
            context.startService(intent)
        }
    }

    override fun onHandleIntent(intent: Intent) {
        val url = intent.getStringExtra("url")
        val id = intent.getStringExtra("id")
        val receiver: ResultReceiver = intent.getParcelableExtra("receiver")
        val index = intent.getIntExtra("index", -1)
        val bundle = Bundle()
        val query = PictureContent.QUERY
        val name = "$query$id$index.png"
        val path = "$cacheDir/$name"
        Log.d("onHandleIntent", "Check file $path")
        val response = loadPicture(url, path)
        bundle.putString("filePath", path)
        if (index != -1)
            bundle.putInt("index", index)
        receiver.send(response, bundle)
        Log.d("onHandleIntent", "Result sent")
    }

    private fun loadPicture(urlString: String, path: String): Int {
        val file = File(path)
        if (!file.exists()) {
            try {
                Log.d("loadPicture", "File $path created")
                file.createNewFile()
                val downloadURL = URL(urlString)
                val connection = downloadURL.openConnection()
                val inputStream = connection.getInputStream()
                val outputStream = FileOutputStream(path)
                val buffer = ByteArray(connection.contentLength)
                var readSize = 0
                var currentlyRead: Int
                Log.d("loadPicture", "Load started")
                while (readSize < buffer.size) {
                    currentlyRead = inputStream.read(buffer, readSize, buffer.size - readSize)
                    outputStream.write(buffer, readSize, currentlyRead)
                    readSize += currentlyRead
                }
                Log.d("loadPicture", "Load finished")
                inputStream.close()
                outputStream.close()
                return DOWNLOAD_SUCCESS
            } catch (e: Exception) {
                e.printStackTrace()
                return DOWNLOAD_ERROR
            }
        } else {
            Log.d("loadPicture", "File already exists")
            return DOWNLOAD_CANCELLED
        }

    }
}