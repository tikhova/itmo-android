package com.tikhova.picturesnetworking

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.tikhova.picturesnetworking.dummy.PictureContent
import kotlinx.android.synthetic.main.activity_picture_list.*
import kotlinx.android.synthetic.main.picture_list.*
import kotlinx.android.synthetic.main.picture_list_content.view.*

class PictureListActivity : AppCompatActivity() {

    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        if (picture_detail_container != null) {
            twoPane = true
        }

        setupRecyclerView(picture_list)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        val adapter = SimpleItemRecyclerViewAdapter(this, PictureContent.ITEMS, twoPane)
        recyclerView.adapter = adapter
        PictureContent.ADAPTER = adapter
    }

    inner class SimpleItemRecyclerViewAdapter(
        private val parentActivity: PictureListActivity,
        private val values: List<PictureContent.PictureItem>,
        private val twoPane: Boolean
    ) :
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {


        inner class ImageResultReceiver : ResultReceiver(Handler()) {
            val DOWNLOAD_SUCCESS = 2

            override fun onReceiveResult(resultCode: Int, resultData: Bundle) {
                Log.d("Receiver", "Result received")
                if (resultCode == DOWNLOAD_SUCCESS) {
                    Log.d("Receiver", "Result understood")
                    val index = resultData.getInt("index")
                    val filePath = resultData.getString("filePath")
                    Log.d("Receiver", "Path is $filePath")
                    val bmp = BitmapFactory.decodeFile(filePath)
                    PictureContent.ITEMS[index].thumb = bmp
                    PictureContent.ADAPTER.notifyItemChanged(index)
                    Log.d("Receiver", "Image set")
                }

                super.onReceiveResult(resultCode, resultData)
            }
        }

        private val imageResultReceiver = ImageResultReceiver()
        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag as PictureContent.PictureItem
                if (twoPane) {
                    val fragment = PictureDetailFragment().apply {
                        arguments = Bundle().apply {
                            putString(PictureDetailFragment.ARG_ITEM_ID, item.id)
                        }
                    }
                    parentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.picture_detail_container, fragment)
                        .commit()
                } else {
                    val intent = Intent(v.context, PictureDetailActivity::class.java).apply {
                        putExtra(PictureDetailFragment.ARG_ITEM_ID, item.id)
                    }
                    v.context.startActivity(intent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.picture_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
            PictureLoaderService().load(parentActivity.applicationContext, item.thumbnailURL, item.id,
                imageResultReceiver, position)
            holder.contentView.text = item.description
            holder.imgView.setImageBitmap(item.thumb)

            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val imgView: ImageView = view.thumb
            val contentView: TextView = view.content
        }

    }

    private fun clearCache() {
        val files = cacheDir.listFiles()
        if (files != null) {
            for (file in files)
                file.delete()
        }
    }

    override fun onDestroy() {
        clearCache()
        super.onDestroy()
    }
}


