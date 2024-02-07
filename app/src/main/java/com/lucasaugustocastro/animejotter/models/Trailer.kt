package com.lucasaugustocastro.animejotter.models


import com.google.gson.annotations.SerializedName

data class Trailer(
    @SerializedName("embed_url")
    val embedUrl: Any?,
    @SerializedName("images")
    val images: ImagesX?,
    @SerializedName("url")
    val url: Any?,
    @SerializedName("youtube_id")
    val youtubeId: Any?
)