package com.tikhova.picturesnetworking.mvp.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.tikhova.picturesnetworking.mvp.models.picture.PictureContent
import com.tikhova.picturesnetworking.mvp.views.ListView

@InjectViewState
class ListPresenter : MvpPresenter<ListView>() {

    fun onGetNewList(text: String, count: Int) {
        PictureContent.getList(text, count)
        viewState.refresh()
    }
}