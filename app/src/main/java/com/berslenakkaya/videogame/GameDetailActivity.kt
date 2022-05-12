package com.berslenakkaya.videogame

import android.os.Bundle
import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.isVisible
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GameDetailActivity : AppCompatActivity() {
    var addToFavIcon: ImageView? = null
    var game:VideoGame? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_detail)

        val gameImage = findViewById<ImageView>(R.id.gameImage)
        val gameName = findViewById<TextView>(R.id.gameName)
        val loading = findViewById<ImageView>(R.id.loading)
        val gameRate = findViewById<TextView>(R.id.gameRate)
        val gameReleaseDate = findViewById<TextView>(R.id.gameReleaseDate)
        val gameDescription = findViewById<TextView>(R.id.gameDescription)
        addToFavIcon = findViewById(R.id.addToFavIcon)

        val gameId = intent.getExtras()?.getInt("gameId")
        loading.isVisible = true

        ApiClient.getApiService(BuildConfig.API_KEY).getGameDetail(gameId!!,BuildConfig.API_KEY).enqueue(object :
            Callback<VideoGame> {
            override fun onFailure(call: Call<VideoGame>, t: Throwable) {
            }

            override fun onResponse(call: Call<VideoGame>, response: Response<VideoGame>) {
                loading.isVisible = false
                game = response.body() as VideoGame
                Picasso.get()
                    .load(game!!.background_image)
                    .fit()
                    .placeholder(R.drawable.loading)
                    .centerCrop()
                    .into(gameImage);
                gameName.text = game!!.name
                gameRate.text = game!!.rating.toString() + " / 5"
                gameReleaseDate.text = game!!.released
                gameDescription.text = Html.fromHtml(game!!.description)
                if(Constants.allGames!!.any{it.isFavorited && it.id == game!!.id}) {
                    DrawableCompat.setTint(
                        DrawableCompat.wrap(addToFavIcon!!.getDrawable()),
                        ContextCompat.getColor(baseContext, R.color.red)
                    )
                }else{
                    DrawableCompat.setTint(
                        DrawableCompat.wrap(addToFavIcon!!.getDrawable()),
                        ContextCompat.getColor(baseContext, R.color.purple_500)
                    )
                }

            }
        })

        addToFavIcon!!.setOnClickListener{
            if(game!=null && Constants.allGames!=null && Constants.allGames!!.count() > 0){
                if(!Constants.allGames!!.any{it.isFavorited && it.id == game!!.id}) {
                    Constants.allGames!!.find { it.id == game!!.id }!!.isFavorited = true
                    game!!.isFavorited = true
                    DrawableCompat.setTint(
                        DrawableCompat.wrap(addToFavIcon!!.getDrawable()),
                        ContextCompat.getColor(baseContext, R.color.red)
                    )
                }else{
                    Constants.allGames!!.find { it.id == game!!.id }!!.isFavorited = false
                    game!!.isFavorited = false
                    DrawableCompat.setTint(
                        DrawableCompat.wrap(addToFavIcon!!.getDrawable()),
                        ContextCompat.getColor(baseContext, R.color.purple_500)
                    )
                }
            }
        }
    }
}