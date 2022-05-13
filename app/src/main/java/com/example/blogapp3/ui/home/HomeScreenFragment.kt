package com.example.blogapp3.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.blogapp3.R
import com.example.blogapp3.core.Result
import com.example.blogapp3.core.hide
import com.example.blogapp3.core.show
import com.example.blogapp3.data.model.Post
import com.example.blogapp3.data.model.remote.home.HomeScreenDataSource
import com.example.blogapp3.databinding.FragmentHomeScreenBinding
import com.example.blogapp3.domain.home.HomeScreenRepoImpl
import com.example.blogapp3.presentation.HomeScreenViewModel
import com.example.blogapp3.presentation.HomeScreenViewModelFactory
import com.example.blogapp3.ui.home.adapter.HomeScreenAdapter
import com.example.blogapp3.ui.home.adapter.OnPostClickListener

class HomeScreenFragment : Fragment(R.layout.fragment_home_screen), OnPostClickListener {

    private lateinit var binding: FragmentHomeScreenBinding
    private val viewModel by viewModels<HomeScreenViewModel> {
        HomeScreenViewModelFactory(
            HomeScreenRepoImpl(
                HomeScreenDataSource()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeScreenBinding.bind(view)

        viewModel.fetchLatestPost().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.show()
                }

                is Result.Success -> {
                    binding.progressBar.hide()
                    if (result.data.isEmpty()) {
                        binding.emptyContainer.show()
                        return@Observer
                    }else{
                        binding.emptyContainer.hide()
                    }
                    binding.rvHome.adapter = HomeScreenAdapter(result.data, this)
                }

                is Result.Failure -> {
                    binding.progressBar.hide()
                    Toast.makeText(
                        requireContext(),
                        "Ocurrio un error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        })
    }

    override fun onLikeButtonClick(post: Post, liked: Boolean) {
        viewModel.registerLikeButtonState(post.id, liked).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {

                }

                is Result.Success -> {

                }

                is Result.Failure -> {
                    Toast.makeText(
                        requireContext(),
                        "Ocurrio un error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}