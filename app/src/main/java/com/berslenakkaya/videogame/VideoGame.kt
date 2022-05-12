package com.berslenakkaya.videogame

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class VideoGame(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("background_image")
    val background_image: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("released")
    val released: String,
    var isFavorited: Boolean
): Serializable
