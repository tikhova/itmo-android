package com.tikhova.picturesnetworking.mvp.models.picture

import com.google.gson.annotations.SerializedName

class PicturesWrapper {
    @SerializedName("results")
    var results: List<Picture>? = null
}