package com.tikhova.picturesnetworking.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.tikhova.picturesnetworking.R
import com.tikhova.picturesnetworking.mvp.models.picture.Picture
import com.tikhova.picturesnetworking.mvp.models.picture.PictureContent
import kotlinx.android.synthetic.main.picture_detail.view.*

class PictureDetailFragment : Fragment() {
    private var item: Picture? = null
    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                item = PictureContent.ITEM_MAP[it.getString(ARG_ITEM_ID)!!]
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.picture_detail, container, false)
        showPicture()
        return rootView
    }

    companion object {
        const val ARG_ITEM_ID = "item_id"
    }

    override fun onDestroy() {
        super.onDestroy()
        cancelDownload()
    }

    fun showPicture() {
        item?.let {
            Picasso.get()
                .load(item!!.urls!!.regular)
                .into(rootView.detail_imgView)
        }
    }

    fun cancelDownload() {
        Picasso.get().cancelTag(this)
    }
}
