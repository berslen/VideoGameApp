package com.berslenakkaya.videogame.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.berslenakkaya.videogame.Constants
import com.berslenakkaya.videogame.VideoGame
import com.berslenakkaya.videogame.VideoGameAdapter
import com.berslenakkaya.videogame.databinding.FragmentFavoritesBinding


class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(FavoritesViewModel::class.java)

        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.favoritesRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.favoritesRecyclerView.adapter = VideoGameAdapter(Constants.allGames!!.filter{it.isFavorited} as ArrayList<VideoGame>)
        if(Constants.allGames!!.filter{it.isFavorited}.count() > 0){
            binding.emptyPage.isVisible = false
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}