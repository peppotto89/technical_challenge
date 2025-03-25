package com.example.hintochallenge.data.repositories

import com.example.hintochallenge.data.api.ApiService
import com.example.hintochallenge.data.model.Post
import com.example.hintochallenge.toDomain
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiService: ApiService): Repository {
    override suspend fun getPosts(): Result<List<Post>> {
        return try {
            val response = apiService.getPosts()
            if (response.isSuccessful) {
                response.body()?.let { dtoList ->
                    val posts = dtoList.map{ it.toDomain() }
                    Result.success(posts)
                } ?: Result.failure(Exception("Response body is null"))
            } else {
                Result.failure(Exception("API error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}