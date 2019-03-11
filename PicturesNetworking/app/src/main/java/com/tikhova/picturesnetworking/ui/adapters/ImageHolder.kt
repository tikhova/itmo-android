package com.tikhova.picturesnetworking.ui.adapters

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.tikhova.picturesnetworking.mvp.models.picture.Picture
import com.tikhova.picturesnetworking.mvp.views.PictureHolderView
import com.tikhova.picturesnetworking.ui.activities.PictureDetailActivity
import com.tikhova.picturesnetworking.ui.fragments.PictureDetailFragment
import kotlinx.android.synthetic.main.picture_list_content.view.*


class ImageHolder(
    itemView: View,
    parentActivity: AppCompatActivity,
    twoPane: Boolean
) : RecyclerView.ViewHolder(itemView), PictureHolderView {
    var imgView: ImageView = itemView.thumb
    val contentView: TextView = itemView.content

    private val onClickListener = View.OnClickListener { v ->
        val item = v.tag as Picture
        if (twoPane) {
            val fragment = PictureDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(PictureDetailFragment.ARG_ITEM_ID, item.id)
                }
            }
            parentActivity.supportFragmentManager
                .beginTransaction()
                .replace(com.tikhova.picturesnetworking.R.id.picture_detail_container, fragment)
                .commit()
        } else {
            val intent = Intent(v.context, PictureDetailActivity::class.java).apply {
                putExtra(PictureDetailFragment.ARG_ITEM_ID, item.id)
            }
            v.context.startActivity(intent)
        }
    }

    override fun cleanup() {
        Picasso.get().cancelRequest(imgView)
        imgView.setImageDrawable(null)
    }

    override fun setupItemInformation(pic: Picture) {
        this.contentView.text = pic.description
        Picasso.get()
            .load(pic.urls!!.thumb)
            .into(this.imgView)
    }

    override fun setItemOnClickListener(pic: Picture) {
        with(this.itemView) {
            tag = pic
            setOnClickListener(onClickListener)
        }
    }
}