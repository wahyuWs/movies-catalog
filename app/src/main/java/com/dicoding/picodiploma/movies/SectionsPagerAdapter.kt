package com.dicoding.picodiploma.movies

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dicoding.picodiploma.movies.movie.MovieFragment
import com.dicoding.picodiploma.movies.tvshow.TvShowFragment

class SectionsPagerAdapter(private val mContext: Context, fm: FragmentManager): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        @StringRes
        private val tabTitle = intArrayOf(R.string.movie, R.string.tv_show)
    }

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment =
            when(position) {
                0 -> MovieFragment()
                1 -> TvShowFragment()
                else -> Fragment()
            }

    override fun getPageTitle(position: Int): CharSequence? = mContext.resources.getString(tabTitle[position])
}