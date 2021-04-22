package com.dicoding.picodiploma.movies.tvshow

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.movies.data.source.local.entity.DataTvShow
import com.dicoding.picodiploma.movies.detail.DetailActivity
import com.dicoding.picodiploma.movies.databinding.ItemsTvshowBinding

class TvShowAdapter: PagedListAdapter<DataTvShow, TvShowAdapter.TvShowViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataTvShow>() {
            override fun areItemsTheSame(oldItem: DataTvShow, newItem: DataTvShow): Boolean =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: DataTvShow, newItem: DataTvShow): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val itemsTvShowBinding = ItemsTvshowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(itemsTvShowBinding)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val tvShow = getItem(position)
        if (tvShow != null) {
            holder.bind(tvShow)
        }
    }

    inner class TvShowViewHolder(private val binding: ItemsTvshowBinding): RecyclerView.ViewHolder(binding.root) {
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