package com.berslenakkaya.videogame.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.berslenakkaya.videogame.ApiClient
import com.berslenakkaya.videogame.BuildConfig
import com.berslenakkaya.videogame.VideoGameResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    val gamesLiveData : MutableLiveData<VideoGameResponseModel?> = MutableLiveData()

    fun getMovies(token:String){
        ApiClient.getApiService(token).getGames(BuildConfig.API_KEY).enqueue(object : Callback<VideoGameResponseModel>{
            override fun onFailure(call: Call<VideoGameResponseModel>, t: Throwable) {
                gamesLiveData.postValue(null)
            }

            override fun onResponse(call: Call<VideoGameResponseModel>, response: Response<VideoGameResponseModel>) {
                gamesLiveData.postValue(response.body())
            }
        })
    }
}

