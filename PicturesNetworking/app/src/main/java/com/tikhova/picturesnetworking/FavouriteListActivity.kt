package com.tikhova.picturesnetworking

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import com.squareup.picasso.Picasso
import com.tikhova.picturesnetworking.picture.PictureContent
import kotlinx.android.synthetic.main.activity_picture_list.*
import kotlinx.android.synthetic.main.picture_list.*


class FavouriteListActivity : AppCompatActivity() {

    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_list)
        setSupportActionBar(toolbar)
        toolbar.title = title
        if (picture_detail_container != null) {
            twoPane = true
        }
        setupRecyclerView(picture_list)

        search_bar.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(cs: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}
            override fun beforeTextChanged(cs: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}
            override fun afterTextChanged(text: Editable) {
               // PictureContent.getList(text.toString(), Companion.COUNT)
            }
        })
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        val adapter = RecyclerViewAdapter(this, PictureContent.ITEMS, twoPane)
        recyclerView.adapter = adapter
        PictureContent.ADAPTER = adapter
    }

    override fun onDestroy() {
        Picasso.get().cancelTag(this)
        super.onDestroy()
    }

    companion object {
        const val COUNT = 25
    }
}
