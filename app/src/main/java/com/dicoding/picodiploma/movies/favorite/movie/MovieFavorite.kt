package com.dicoding.picodiploma.movies.favorite.movie

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.movies.R
import com.dicoding.picodiploma.movies.databinding.FragmentMovieFavoriteBinding
import com.dicoding.picodiploma.movies.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class MovieFavorite : Fragment() {

    private var _fragmentMovieFavorite: FragmentMovieFavoriteBinding? = null
    private val binding get() = _fragmentMovieFavorite

    private lateinit var viewModel: MovieFavoriteViewModel
    private lateinit var movieFavoriteAdapter: MovieFavoriteAdapter

    private val valueDelay: Long = 2000

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _fragmentMovieFavorite = FragmentMovieFavoriteBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(binding?.rvMovieFavorite)

        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[MovieFavoriteViewModel::class.java]

        movieFavoriteAdapter = MovieFavoriteAdapter()
        viewModel.getFavoriteMovies().observe(this, { movieFavorite ->
            movieFavoriteAdapter.submitList(movieFavorite)
        })

        binding?.swipeRefreshMovie?.setOnRefreshListener {
            Handler(Looper.getMainLooper()).postDelayed({
                binding?.swipeRefreshMovie?.isRefreshing = false
                viewModel.getFavoriteMovies().observe(this, { movieFavorite ->
                    movieFavoriteAdapter.submitList(movieFavorite)
                })
            }, valueDelay)
        }

        with(binding?.rvMovieFavorite) {
            this?.layoutManager =LinearLayoutManager(context)
            this?.setHasFixedSize(true)
            this?.adapter = movieFavoriteAdapter
        }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int =
                makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val movieFavorite = movieFavoriteAdapter.getSwipedData(swipedPosition)
                movieFavorite?.let { viewModel.setMovieFavorite(it) }

                val snackbar = Snackbar.make(view as View, R.string.message_cancel, Snackbar.LENGTH_LONG)
                snackbar.setAction(R.string.message_ok) {
                    movieFavorite?.let { viewModel.setMovieFavorite(it) }
                }
                snackbar.show()
            }
        }
    })
}