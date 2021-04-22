package com.dicoding.picodiploma.movies.favorite.tvshow

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
import com.dicoding.picodiploma.movies.databinding.FragmentTvShowFavoriteBinding
import com.dicoding.picodiploma.movies.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class TvShowFavorite : Fragment() {

    private var _fragmentTvShowFavorite: FragmentTvShowFavoriteBinding? = null
    private val binding get() = _fragmentTvShowFavorite

    private lateinit var viewModel: TvShowFavoriteViewModel
    private lateinit var tvShowFavoriteAdapter: TvShowFavoriteAdapter

    private val valueDelay: Long = 2000

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _fragmentTvShowFavorite = FragmentTvShowFavoriteBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(binding?.rvTvShowFavorite)

        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[TvShowFavoriteViewModel::class.java]

        tvShowFavoriteAdapter = TvShowFavoriteAdapter()
        viewModel.getFavoriteTvShows().observe(this, { tvShowFavorite ->
            tvShowFavoriteAdapter.submitList(tvShowFavorite)
        })

        binding?.swipeRefreshTvShow?.setOnRefreshListener {
            Handler(Looper.getMainLooper()).postDelayed({
                binding?.swipeRefreshTvShow?.isRefreshing = false
                viewModel.getFavoriteTvShows().observe(this, { tvShowFavorite ->
                    tvShowFavoriteAdapter.submitList(tvShowFavorite)
                })
            }, valueDelay)
        }

        with(binding?.rvTvShowFavorite) {
            this?.layoutManager = LinearLayoutManager(context)
            this?.setHasFixedSize(true)
            this?.adapter = tvShowFavoriteAdapter
        }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int =
                makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val tvShowFavorite = tvShowFavoriteAdapter.getSwipedData(swipedPosition)
                tvShowFavorite?.let { viewModel.setFavoriteTvShow(it) }

                val snackbar = Snackbar.make(view as View, R.string.message_cancel, Snackbar.LENGTH_LONG)
                snackbar.setAction(R.string.message_ok) {
                    tvShowFavorite?.let { viewModel.setFavoriteTvShow(it) }
                }
                snackbar.show()
            }
        }
    })
}