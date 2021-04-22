package com.dicoding.picodiploma.movies.favorite.tvshow

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.movies.data.source.local.entity.DataTvShow
import com.dicoding.picodiploma.movies.databinding.ItemsTvshowBinding
import com.dicoding.picodiploma.movies.detail.DetailActivity

class TvShowFavoriteAdapter: PagedListAdapter<DataTvShow, TvShowFavoriteAdapter.TvShowFavoriteViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataTvShow>() {
            override fun areItemsTheSame(oldItem: DataTvShow, newItem: DataTvShow): Boolean =
                    oldItem.favorite == newItem.favorite

            override fun areContentsTheSame(oldItem: DataTvShow, newItem: DataTvShow): Boolean =
                    oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowFavoriteViewHolder {
        val itemsTvshowBinding = ItemsTvshowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowFavoriteViewHolder(itemsTvshowBinding)
    }

    override fun onBindViewHolder(holder: TvShowFavoriteViewHolder, position: Int) {
        val tvShow = getItem(position)
        if (tvShow != null) {
            holder.bind(tvShow)
        }
    }

    fun getSwipedData(swipedPosition: Int): DataTvShow? = getItem(swipedPosition)

    inner class TvShowFavoriteViewHolder(private val binding: ItemsTvshowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: DataTvShow) {
            with(binding) {
                Glide.with(itemView.context)
                        .load(tvShow.image)
                        .into(imgTvshow)

                tvshowTitle.text = tvShow.title
                tvshowGenre.text = tvShow.genre
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_TV_SHOW, tvShow.title)
                    itemView.context.startActivity(intent)
                }
            }
        }

    }
}