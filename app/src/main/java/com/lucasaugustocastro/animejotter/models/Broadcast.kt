package com.lucasaugustocastro.animejotter.models


import com.google.gson.annotations.SerializedName

data class Broadcast(
    @SerializedName("day")
    val day: Any?,
    @SerializedName("string")
    val string: String?,
    @SerializedName("time")
    val time: Any?,
    @SerializedName("timezone")
    val timezone: Any?
)