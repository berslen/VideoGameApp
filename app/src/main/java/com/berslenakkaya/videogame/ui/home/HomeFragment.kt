package com.berslenakkaya.videogame.ui.home

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.berslenakkaya.videogame.*
import com.berslenakkaya.videogame.databinding.FragmentHomeBinding
import java.util.*

class HomeFragment : Fragment(){
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var searchResult: List<VideoGame>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.loadingImage.isVisible = true

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        homeViewModel.getMovies(BuildConfig.API_KEY)
        homeViewModel.gamesLiveData.observeForever {
            if (it!=null) {
                if(Constants.allGames!!.count() == 0){
                    Constants.allGames = it.results
                    binding.loadingImage.isVisible = false
                    binding.viewPager.adapter = ViewPagerAdapter(Constants.allGames!!.take(3) as ArrayList<VideoGame>)
                    binding.recyclerView.adapter = VideoGameAdapter(Constants.allGames!!.takeLast(Constants.allGames!!.size - 3) as ArrayList<VideoGame>)
                    binding.dotsIndicator.attachTo(binding.viewPager)
                }else{
                    binding.loadingImage.isVisible = false
                    binding.viewPager.adapter = ViewPagerAdapter(Constants.allGames!!.take(3) as ArrayList<VideoGame>)
                    binding.dotsIndicator.attachTo(binding.viewPager)
                }
            }
        }

        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(Constants.allGames!=null && Constants.allGames!!.count() > 0){
                    if(count>=3){
                        searchResult = searchGame(s.toString().trim())
                        if(searchResult!!.count() == 0){
                            binding.emptySearchResult.isVisible = true
                        }else{
                            binding.emptySearchResult.isVisible = false
                            binding.linearLayout.isVisible = false
                        }
                        binding.recyclerView.adapter = VideoGameAdapter(searchResult as ArrayList<VideoGame>)
                        binding.searchImage.setImageResource(R.drawable.ic_cancel)
                        binding.searchImage.setOnClickListener{
                            it.hideKeyboard()
                            it.clearFocus()
                            binding.searchInput.text.clear()
                            binding.recyclerView.adapter = VideoGameAdapter(Constants.allGames!!.takeLast(Constants.allGames!!.size - 3) as ArrayList<VideoGame>)
                            binding.recyclerView.adapter!!.notifyDataSetChanged()
                            binding.searchImage.setImageResource(R.drawable.ic_search)
                        }
                    }else if(count == 0 || s.toString().equals("")){
                        binding.searchImage.setOnClickListener(null)
                        binding.emptySearchResult.isVisible = false
                        binding.linearLayout.isVisible = true
                        binding.searchImage.setImageResource(R.drawable.ic_search)
                        binding.recyclerView.adapter = VideoGameAdapter(Constants.allGames!!.takeLast(Constants.allGames!!.size - 3) as ArrayList<VideoGame>)
                        binding.recyclerView.adapter!!.notifyDataSetChanged()
                    }
                }
            }
        })
    }

    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    fun searchGame(term: String): List<VideoGame> {
        return Constants.allGames!!.filter {it.name.lowercase(Locale.getDefault()).contains(term.lowercase(Locale.getDefault()))}
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}