package com.tikhova.picturesnetworking

import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tikhova.picturesnetworking.dummy.PictureContent
import kotlinx.android.synthetic.main.picture_detail.*

class PictureDetailFragment : Fragment() {

    inner class ImageResultReceiver : ResultReceiver(Handler()) {
        override fun onReceiveResult(resultCode: Int, resultData: Bundle) {
            val filePath = resultData.getString("filePath")
            val bmp = BitmapFactory.decodeFile(filePath)
            detail_imgView.setImageBitmap(bmp)
            super.onReceiveResult(resultCode, resultData)
        }
    }

    private val imageResultReceiver = ImageResultReceiver()
    private var item: PictureContent.PictureItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID))
                item = PictureContent.ITEM_MAP[it.getString(ARG_ITEM_ID)!!]
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.picture_detail, container, false)

        item?.let {
            PictureLoaderService().load(this.context!!, item!!.fullURL, item!!.id, imageResultReceiver)
        }

        return rootView
    }

    companion object {
        const val ARG_ITEM_ID = "item_id"
    }
}



