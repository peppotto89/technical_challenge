package com.example.hintochallenge.data.usecase

import com.example.hintochallenge.data.repositories.RepositoryDb
import javax.inject.Inject

class CheckFavoriteUseCase @Inject constructor(
    private val repository: RepositoryDb
) {
    suspend operator fun invoke(postId: Int): Boolean {
        return repository.checkFavorite(postId) > 0
    }
}