package com.berslenakkaya.videogame

import com.google.gson.annotations.SerializedName

data class VideoGameResponseModel(
    @SerializedName("results")
    val results: ArrayList<VideoGame>,
)
