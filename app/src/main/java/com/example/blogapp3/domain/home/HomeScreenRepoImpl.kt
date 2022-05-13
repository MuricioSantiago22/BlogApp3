package com.example.blogapp3.domain.home

import com.example.blogapp3.core.Result
import com.example.blogapp3.data.model.Post
import com.example.blogapp3.data.model.remote.home.HomeScreenDataSource


class HomeScreenRepoImpl(private val dataSource: HomeScreenDataSource): HomeScreenRepo {
    override suspend fun getLatestPosts(): Result<List<Post>> = dataSource.getLatestPosts()

    override suspend fun registerLikeButtonState(postId: String, liked: Boolean) = dataSource.registerLikeButtonState(postId, liked)
}