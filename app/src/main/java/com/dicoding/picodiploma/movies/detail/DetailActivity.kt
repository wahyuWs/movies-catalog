package com.dicoding.picodiploma.movies.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.dicoding.picodiploma.movies.R
import com.dicoding.picodiploma.movies.data.source.local.entity.DataMovie
import com.dicoding.picodiploma.movies.data.source.local.entity.DataTvShow
import com.dicoding.picodiploma.movies.databinding.ActivityDetailBinding
import com.dicoding.picodiploma.movies.viewmodel.ViewModelFactory
import com.dicoding.picodiploma.movies.vo.Status

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
        const val EXTRA_TV_SHOW = "extra_tv_show"
    }

    private var _activityDetailBinding: ActivityDetailBinding? = null

    private val detailBinding get() = _activityDetailBinding
    private val itemBinding get() = _activityDetailBinding?.itemDetail

    private lateinit var viewModel: DetailActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)

        setContentView(detailBinding?.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0f

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailActivityViewModel::class.java]

        val dataMovie = intent.getStringExtra(EXTRA_MOVIE)
        val dataTvShow = intent.getStringExtra(EXTRA_TV_SHOW)

        if (dataMovie != null) {
            supportActionBar?.title = resources.getString(R.string.movie)
            viewModel.setMovie(dataMovie)

            viewModel.detailMovie.observe(this, { detailMovie ->
                if (detailMovie != null) {
                    when (detailMovie.status) {
                        Status.LOADING -> detailBinding?.progressBar?.visibility = View.VISIBLE
                        Status.SUCCESS -> {
                            detailBinding?.progressBar?.visibility = View.GONE
                            detailMovie.data?.let { setDetailMovie(it) }
                        }
                        Status.ERROR -> {
                            detailBinding?.progressBar?.visibility = View.GONE
                            Toast.makeText(this, "Terdapat kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        } else if (dataTvShow != null) {
            supportActionBar?.title = resources.getString(R.string.tv_show)
            viewModel.setTvShow(dataTvShow)

            viewModel.detailTvShow.observe(this, { detailTvShow ->
                if (detailTvShow != null) {
                    when (detailTvShow.status) {
                        Status.LOADING -> detailBinding?.progressBar?.visibility = View.VISIBLE
                        Status.SUCCESS -> {
                            detailBinding?.progressBar?.visibility = View.GONE
                            detailTvShow.data?.let { setDetailTvShow(it) }
                        }
                        Status.ERROR -> {
                            detailBinding?.progressBar?.visibility = View.GONE
                            Toast.makeText(this, "Terdapat kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }
    }

    private fun setDetailMovie(data: DataMovie) {
        detailBinding?.let {
            Glide.with(this)
                .load(data.image)
                .transform(RoundedCorners(30))
                .into(it.imageMovie)
        }


        detailBinding?.titleMovie?.text = data.title
        detailBinding?.rating?.text = data.rate
        if (data.favorite) {
            detailBinding?.statusFavorite?.setImageResource(R.drawable.ic_favorite)
            detailBinding?.statusFavorite?.setOnClickListener {
                viewModel.setMovieFavorite()
                Toast.makeText(this, resources.getString(R.string.toast_delete), Toast.LENGTH_SHORT).show()
            }
        } else {
            detailBinding?.statusFavorite?.setImageResource(R.drawable.ic_favorite_border)
            detailBinding?.statusFavorite?.setOnClickListener {
                viewModel.setMovieFavorite()
                Toast.makeText(this, resources.getString(R.string.toast_add), Toast.LENGTH_SHORT).show()
            }
        }


        with(itemBinding) {
            this?.movieDescription?.text = data.description
            this?.genre?.text = data.genre
            this?.releaseDate?.text = data.releaseDate
            this?.duration?.text = data.duration
            this?.status?.text = data.status
            this?.imageShare?.setOnClickListener {
                val mimeType = "text/plain"
                ShareCompat.IntentBuilder
                        .from(this@DetailActivity)
                        .setType(mimeType)
                        .setChooserTitle(resources.getString(R.string.share_konten))
                        .setText(resources.getString(R.string.share_text, data.title))
                        .startChooser()
            }
        }
    }

    private fun setDetailTvShow(data: DataTvShow) {
        detailBinding?.let {
            Glide.with(this)
                .load(data.image)
                .transform(RoundedCorners(30))
                .into(it.imageMovie)
        }

        detailBinding?.titleMovie?.text = data.title
        detailBinding?.rating?.text = data.rate
        if (data.favorite) {
            detailBinding?.statusFavorite?.setImageResource(R.drawable.ic_favorite)
            detailBinding?.statusFavorite?.setOnClickListener {
                viewModel.setTvShowFavorite()
                Toast.makeText(this, resources.getString(R.string.toast_delete), Toast.LENGTH_SHORT).show()
            }
        } else {
            detailBinding?.statusFavorite?.setImageResource(R.drawable.ic_favorite_border)
            detailBinding?.statusFavorite?.setOnClickListener {
                viewModel.setTvShowFavorite()
                Toast.makeText(this, resources.getString(R.string.toast_add), Toast.LENGTH_SHORT).show()
            }
        }

        with(itemBinding) {
            this?.movieDescription?.text = data.description
            this?.genre?.text = data.genre
            this?.releaseDate?.text = data.releaseDate
            this?.duration?.text = data.duration
            this?.status?.text = data.status
            this?.imageShare?.setOnClickListener {
                val mimeType = "text/plain"
                ShareCompat.IntentBuilder
                        .from(this@DetailActivity)
                        .setType(mimeType)
                        .setChooserTitle(resources.getString(R.string.share_konten))
                        .setText(resources.getString(R.string.share_text, data.title))
                        .startChooser()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityDetailBinding = null
    }
}