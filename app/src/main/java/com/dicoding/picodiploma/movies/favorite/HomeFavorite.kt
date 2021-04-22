package com.dicoding.picodiploma.movies.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dicoding.picodiploma.movies.R
import com.dicoding.picodiploma.movies.databinding.ActivityHomeFavoriteBinding

class HomeFavorite : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val activityHomeFavoriteBinding = ActivityHomeFavoriteBinding.inflate(layoutInflater)
        setContentView(activityHomeFavoriteBinding.root)

        val sectionsPagerAdaptor = SectionPagerAdapterFavorite(this, supportFragmentManager)
        activityHomeFavoriteBinding.viewPagerFavorite.adapter = sectionsPagerAdaptor
        activityHomeFavoriteBinding.tabLayoutFavorite.setupWithViewPager(activityHomeFavoriteBinding.viewPagerFavorite)

        supportActionBar?.elevation = 0f
        supportActionBar?.title = resources.getString(R.string.favorite_text)

        Toast.makeText(this, resources.getString(R.string.swiped_message), Toast.LENGTH_SHORT).show()
    }
}