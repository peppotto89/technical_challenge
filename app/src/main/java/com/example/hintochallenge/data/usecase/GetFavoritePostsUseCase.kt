package com.example.hintochallenge.data.usecase

import com.example.hintochallenge.data.model.Post
import com.example.hintochallenge.data.repositories.RepositoryDb
import javax.inject.Inject

class GetFavoritePostsUseCase @Inject constructor(
    private val repositoryDb: RepositoryDb
) {
    suspend operator fun invoke(): Result<List<Post>> {
        return repositoryDb.getAllPosts()
    }

}