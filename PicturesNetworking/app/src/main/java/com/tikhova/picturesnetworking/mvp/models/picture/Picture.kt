package com.tikhova.picturesnetworking.mvp.models.picture

data class Picture(
    var id: String?,
    var description: String?,
    var urls: Urls?
)

data class Urls(
    var regular: String?,
    var thumb: String?
)