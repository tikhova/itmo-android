package com.tikhova.picturesnetworking.ui.activities

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.tikhova.picturesnetworking.R
import com.tikhova.picturesnetworking.mvp.models.picture.PictureContent
import com.tikhova.picturesnetworking.mvp.presenters.ListPresenter
import com.tikhova.picturesnetworking.mvp.views.ListView
import com.tikhova.picturesnetworking.ui.adapters.RecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_picture_list.*
import kotlinx.android.synthetic.main.picture_list.*


// View

class PictureListActivity : MvpAppCompatActivity(), ListView {
    @InjectPresenter
    lateinit var mListPresenter: ListPresenter

    override fun refresh() {
        picture_list.adapter?.notifyDataSetChanged()
    }

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

        search_bar.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(cs: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}
            override fun beforeTextChanged(cs: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}
            override fun afterTextChanged(text: Editable) = mListPresenter.onGetNewList(text.toString(), 25)
        })
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter =
            RecyclerViewAdapter(this, PictureContent.ITEMS, twoPane)
    }
}
