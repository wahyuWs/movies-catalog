package com.dicoding.picodiploma.movies.favorite

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dicoding.picodiploma.movies.R
import com.dicoding.picodiploma.movies.favorite.movie.MovieFavorite
import com.dicoding.picodiploma.movies.favorite.tvshow.TvShowFavorite

class SectionPagerAdapterFavorite(private val mContext: Context, fm: FragmentManager): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        @StringRes
        private val tabTitle = intArrayOf(R.string.movie, R.string.tv_show)
    }

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment =
            when (position) {
                0 -> MovieFavorite()
                1 -> TvShowFavorite()
                else -> Fragment()
            }

    override fun getPageTitle(position: Int): CharSequence? = mContext.resources.getString(tabTitle[position])
}