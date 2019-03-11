package com.tikhova.picturesnetworking.mvp.presenters

import com.tikhova.picturesnetworking.mvp.models.picture.Picture
import com.tikhova.picturesnetworking.mvp.views.PictureHolderView

class RecyclerViewPresenter {
    fun onSetupItem(item: Picture, holder: PictureHolderView) {
        holder.setupItemInformation(item)
        holder.setItemOnClickListener(item)
    }

    fun onViewRecycled(holder: PictureHolderView) {
        holder.cleanup()
    }
}