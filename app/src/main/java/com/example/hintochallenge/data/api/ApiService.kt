package com.example.hintochallenge.data.api

import com.example.hintochallenge.data.model.PostDto
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/posts")
    suspend fun getPosts(): Response<List<PostDto>>
}