package com.dicoding.picodiploma.movies.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.movies.databinding.FragmentMovieBinding
import com.dicoding.picodiploma.movies.viewmodel.ViewModelFactory
import com.dicoding.picodiploma.movies.vo.Status

class MovieFragment : Fragment() {

    private var _fragmentMovieBinding: FragmentMovieBinding? = null
    private val binding get() = _fragmentMovieBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _fragmentMovieBinding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        val viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]

        val movieAdapter = MovieAdapter()
        viewModel.getMovie().observe(this, { movie ->
            if (movie != null) {
                when (movie.status) {
                    Status.LOADING -> binding?.progressBar?.visibility = View.VISIBLE
                    Status.SUCCESS -> {
                        binding?.progressBar?.visibility = View.GONE
                        movieAdapter.submitList(movie.data)
                    }
                    Status.ERROR -> {
                        binding?.progressBar?.visibility = View.GONE
                        Toast.makeText(context, "Terdapat Kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        with(binding?.rvMovie) {
            this?.layoutManager = LinearLayoutManager(context)
            this?.setHasFixedSize(true)
            this?.adapter = movieAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentMovieBinding = null
    }
}