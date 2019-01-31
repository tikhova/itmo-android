package com.tikhova.picturesnetworking

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.tikhova.picturesnetworking.dummy.PictureContent
import kotlinx.android.synthetic.main.picture_detail.view.*

/**
 * A fragment representing a single Picture detail screen.
 * This fragment is either contained in a [PictureListActivity]
 * in two-pane mode (on tablets) or a [PictureDetailActivity]
 * on handsets.
 */
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class PictureDetailFragment : Fragment() {

    private var item: PictureContent.PictureItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                item = PictureContent.ITEM_MAP[it.getString(ARG_ITEM_ID)]
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.picture_detail, container, false)
        // Show the dummy content as picture in ImageView
        item?.let {
            Picasso.get().load(item?.fullURL).into(rootView.detail_imgView)
        }

        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}
