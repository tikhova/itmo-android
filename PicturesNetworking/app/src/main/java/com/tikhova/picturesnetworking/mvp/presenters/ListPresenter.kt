package com.tikhova.picturesnetworking.mvp.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.tikhova.picturesnetworking.mvp.models.picture.PictureContent
import com.tikhova.picturesnetworking.mvp.views.ListView
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@InjectViewState
class ListPresenter : MvpPresenter<ListView>() {
    fun onGetNewList(text: String, count: Int) {
        runBlocking {
            launch {
                PictureContent.getList(text, count)
                viewState.refresh()
            }
        }
    }
}