package com.berslenakkaya.videogame

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class VideoGameAdapter(val videoGames: ArrayList<VideoGame>) : RecyclerView.Adapter<VideoGameAdapter.ModelViewHolder>() {

    class ModelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val rating: TextView = view.findViewById(R.id.rating)
        val releaseDate: TextView = view.findViewById(R.id.releaseDate)
        val image: ImageView = view.findViewById(R.id.image)

        fun bindItems(item: VideoGame) {
            name.setText(item.name)
            rating.setText(item.rating.toString()+" / 5")
            releaseDate.setText(item.released)
            Picasso.get()
                .load(item.background_image)
                .fit()
                .placeholder(R.drawable.loading)
                .centerCrop()
                .into(image);
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.game_card_view, parent, false)
        return ModelViewHolder(view)
    }

    override fun getItemCount(): Int {
        return videoGames.size
    }

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        holder.bindItems(videoGames.get(position))
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, GameDetailActivity::class.java)
            intent.putExtra("gameId", videoGames.get(position).id)
            holder.itemView.context.startActivity(intent)
        }
    }
}