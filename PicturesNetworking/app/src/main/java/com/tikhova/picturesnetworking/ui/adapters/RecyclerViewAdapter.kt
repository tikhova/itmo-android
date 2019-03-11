package com.tikhova.picturesnetworking.ui.adapters

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tikhova.picturesnetworking.R
import com.tikhova.picturesnetworking.mvp.models.picture.Picture
import com.tikhova.picturesnetworking.mvp.models.picture.PictureContent
import com.tikhova.picturesnetworking.mvp.presenters.RecyclerViewPresenter

class RecyclerViewAdapter(
    private val parentActivity: AppCompatActivity,
    private val values: List<Picture>,
    private val twoPane: Boolean
) : RecyclerView.Adapter<ImageHolder>() {
    private var mRecyclerViewPresenter = RecyclerViewPresenter()
    override fun getItemCount(): Int = PictureContent.COUNT

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.picture_list_content, parent, false)
        return ImageHolder(view, parentActivity, twoPane)
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        val item = values[position]
        mRecyclerViewPresenter.onSetupItem(item, holder)
    }

    override fun onViewRecycled(holder: ImageHolder) {
        mRecyclerViewPresenter.onViewRecycled(holder)
    }


}