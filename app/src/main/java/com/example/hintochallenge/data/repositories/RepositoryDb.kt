package com.example.hintochallenge.data.repositories

import com.example.hintochallenge.data.model.Post
import com.example.hintochallenge.data.model.PostEntity

interface RepositoryDb {
    suspend fun insertPost(post: PostEntity)
    suspend fun getAllPosts(): Result<List<Post>>
    suspend fun deletePostById(postId: Int)
    suspend fun checkFavorite(postId: Int): Int
}