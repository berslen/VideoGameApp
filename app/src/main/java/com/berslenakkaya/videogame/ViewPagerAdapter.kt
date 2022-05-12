package com.berslenakkaya.videogame

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ViewPagerAdapter(val videoGames: ArrayList<VideoGame>) : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerModelViewHolder>() {

    class ViewPagerModelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val viewPageimage: ImageView = view.findViewById(R.id.viewPageimage)

        fun bindItems(item: VideoGame) {
            Picasso.get()
                .load(item.background_image)
                .fit()
                .placeholder(R.drawable.loading)
                .centerCrop()
                .into(viewPageimage);
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerModelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_pager_item, parent, false)
        return ViewPagerModelViewHolder(view)
    }

    override fun getItemCount() = videoGames.size

    override fun onBindViewHolder(holder: ViewPagerModelViewHolder, position: Int) {
        holder.bindItems(videoGames.get(position))
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, GameDetailActivity::class.java)
            intent.putExtra("gameId", videoGames.get(position).id)
            holder.itemView.context.startActivity(intent)
        }
    }
}