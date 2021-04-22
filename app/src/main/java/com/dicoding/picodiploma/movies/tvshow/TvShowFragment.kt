package com.dicoding.picodiploma.movies.tvshow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.movies.databinding.FragmentTvShowBinding
import com.dicoding.picodiploma.movies.viewmodel.ViewModelFactory
import com.dicoding.picodiploma.movies.vo.Status

class TvShowFragment : Fragment() {

    private var _fragmentTvShowBinding: FragmentTvShowBinding? = null
    private val binding get() = _fragmentTvShowBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _fragmentTvShowBinding = FragmentTvShowBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireActivity())
        val viewModel = ViewModelProvider(this, factory)[TvShowViewModel::class.java]

        val tvShowAdapter = TvShowAdapter()
        viewModel.getTvShow().observe(this, { tvShow ->
            if (tvShow != null) {
                when (tvShow.status) {
                    Status.LOADING -> binding?.progressBar?.visibility = View.VISIBLE
                    Status.SUCCESS -> {
                        binding?.progressBar?.visibility = View.GONE
                        tvShowAdapter.submitList(tvShow.data)
                    }
                    Status.ERROR -> {
                        binding?.progressBar?.visibility = View.GONE
                        Toast.makeText(context, "Terdapat kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        with(binding?.rvTvShow) {
            this?.layoutManager = LinearLayoutManager(context)
            this?.setHasFixedSize(true)
            this?.adapter = tvShowAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentTvShowBinding = null
    }
}