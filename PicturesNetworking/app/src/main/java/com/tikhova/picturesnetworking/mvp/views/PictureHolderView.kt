package com.tikhova.picturesnetworking.mvp.views

import com.arellomobile.mvp.MvpView
import com.tikhova.picturesnetworking.mvp.models.picture.Picture

interface PictureHolderView : MvpView {
    fun cleanup()
    fun setupItemInformation(pic: Picture)
    fun setItemOnClickListener(pic: Picture)
}