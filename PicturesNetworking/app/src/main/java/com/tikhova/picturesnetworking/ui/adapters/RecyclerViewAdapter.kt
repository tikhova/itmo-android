package com.tikhova.picturesnetworking.ui.adapters

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.tikhova.picturesnetworking.R
import com.tikhova.picturesnetworking.mvp.models.picture.Picture
import com.tikhova.picturesnetworking.mvp.models.picture.PictureContent
import com.tikhova.picturesnetworking.ui.activities.PictureDetailActivity
import com.tikhova.picturesnetworking.ui.fragments.PictureDetailFragment
import kotlinx.android.synthetic.main.picture_list_content.view.*

// Provider

class RecyclerViewAdapter(
    private val parentActivity: AppCompatActivity,
    private val values: List<Picture>,
    private val twoPane: Boolean
) :
    RecyclerView.Adapter<RecyclerViewAdapter.ImageViewHolder>() {
    override fun getItemCount(): Int = PictureContent.COUNT
    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as Picture
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.picture_list_content, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = values[position]
        holder.contentView.text = item.description
        Picasso.get()
            .load(item.urls!!.thumb)
            .into(holder.imgView)

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun onViewRecycled(holder: ImageViewHolder) {
        holder.cleanup()
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgView: ImageView = itemView.thumb
        val contentView: TextView = itemView.content

        fun cleanup() {
            Picasso.get().cancelRequest(imgView)
            imgView.setImageDrawable(null)
        }
    }

}