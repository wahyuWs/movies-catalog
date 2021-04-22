package com.dicoding.picodiploma.movies.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.dicoding.picodiploma.movies.HomeActivity
import com.dicoding.picodiploma.movies.R

class SplashScreen : AppCompatActivity() {

    private val valueDelay = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashScreen, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }, valueDelay.toLong())
    }
}