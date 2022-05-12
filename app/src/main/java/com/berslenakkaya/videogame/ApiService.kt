package com.berslenakkaya.videogame

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("games")
    fun getGames(@Query("key") apiKey:String): Call<VideoGameResponseModel>

    @GET("games/{id}")
    fun getGameDetail(@Path("id") gameId: Int,@Query("key") apiKey:String): Call<VideoGame>
}