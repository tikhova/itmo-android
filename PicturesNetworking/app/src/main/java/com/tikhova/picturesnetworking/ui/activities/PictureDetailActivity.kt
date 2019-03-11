package com.tikhova.picturesnetworking.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.tikhova.picturesnetworking.R
import com.tikhova.picturesnetworking.ui.fragments.PictureDetailFragment
import kotlinx.android.synthetic.main.activity_picture_detail.*

// View

class PictureDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture_detail)
        setSupportActionBar(detail_toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            val fragment = PictureDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(
                        PictureDetailFragment.ARG_ITEM_ID,
                        intent.getStringExtra(PictureDetailFragment.ARG_ITEM_ID)
                    )
                }
            }

            supportFragmentManager.beginTransaction()
                .add(R.id.picture_detail_container, fragment)
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                navigateUpTo(Intent(this, PictureListActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}
