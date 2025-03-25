package com.example.hintochallenge.data.repositories

import com.example.hintochallenge.data.model.Post

interface Repository {
    suspend fun getPosts(): Result<List<Post>>
}