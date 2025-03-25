package com.example.hintochallenge.data.usecase

import com.example.hintochallenge.data.model.Post
import com.example.hintochallenge.data.repositories.Repository
import javax.inject.Inject

class GetPostUsecase@Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(): Result<List<Post>> {
        return repository.getPosts()
    }
}