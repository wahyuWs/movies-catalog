package com.dicoding.picodiploma.movies.favorite.movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.movies.data.source.local.entity.DataMovie
import com.dicoding.picodiploma.movies.databinding.ItemsMovieBinding
import com.dicoding.picodiploma.movies.detail.DetailActivity

class MovieFavoriteAdapter: PagedListAdapter<DataMovie, MovieFavoriteAdapter.MovieFavoriteViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataMovie>() {
            override fun areItemsTheSame(oldItem: DataMovie, newItem: DataMovie): Boolean =
                    oldItem.favorite == newItem.favorite

            override fun areContentsTheSame(oldItem: DataMovie, newItem: DataMovie): Boolean =
                    oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieFavoriteViewHolder {
        val itemsMovieBinding = ItemsMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieFavoriteViewHolder(itemsMovieBinding)
    }

    override fun onBindViewHolder(holder: MovieFavoriteViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
        }
    }

    fun getSwipedData(swipedPosition: Int): DataMovie? = getItem(swipedPosition)

    inner class MovieFavoriteViewHolder(private val binding: ItemsMovieBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: DataMovie) {
            with(binding) {
                Glide.with(itemView.context)
                        .load(movie.image)
                        .into(imgMovie)

                movieTitle.text = movie.title
                movieGenre.text = movie.genre
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_MOVIE, movie.title)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}