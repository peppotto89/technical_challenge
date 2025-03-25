package com.example.hintochallenge.data.usecase

import com.example.hintochallenge.data.model.Post
import com.example.hintochallenge.data.repositories.RepositoryDb
import com.example.hintochallenge.toEntity
import javax.inject.Inject

class InsertFavoritePostUseCase @Inject constructor(
    private val repositoryDb: RepositoryDb
) {
    suspend operator fun invoke(post: Post) {
        repositoryDb.insertPost(post.toEntity())
    }
}