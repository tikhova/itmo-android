package com.tikhova.picturesnetworking.picture

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class PicturesWrapper {
    @SerializedName("results")
    @Expose
    var results: List<Picture>? = null
}