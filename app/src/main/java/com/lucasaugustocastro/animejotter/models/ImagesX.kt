package com.lucasaugustocastro.animejotter.models


import com.google.gson.annotations.SerializedName

data class ImagesX(
    @SerializedName("image_url")
    val imageUrl: Any?,
    @SerializedName("large_image_url")
    val largeImageUrl: Any?,
    @SerializedName("maximum_image_url")
    val maximumImageUrl: Any?,
    @SerializedName("medium_image_url")
    val mediumImageUrl: Any?,
    @SerializedName("small_image_url")
    val smallImageUrl: Any?
)