package com.example.blogapp3.domain.home

import com.example.blogapp3.core.Result
import com.example.blogapp3.data.model.Post

interface HomeScreenRepo {
    suspend fun getLatestPosts(): Result<List<Post>>
    suspend fun registerLikeButtonState(postId: String, liked: Boolean)
}