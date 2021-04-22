package com.dicoding.picodiploma.movies.movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.movies.data.source.local.entity.DataMovie
import com.dicoding.picodiploma.movies.detail.DetailActivity
import com.dicoding.picodiploma.movies.databinding.ItemsMovieBinding

class MovieAdapter: PagedListAdapter<DataMovie, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataMovie>() {
            override fun areItemsTheSame(oldItem: DataMovie, newItem: DataMovie): Boolean =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: DataMovie, newItem: DataMovie): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemsMovieBinding = ItemsMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemsMovieBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
        }
    }

    inner class MovieViewHolder(private val binding: ItemsMovieBinding): RecyclerView.ViewHolder(binding.root) {
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