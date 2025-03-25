package com.example.hintochallenge.data.repositories

import com.example.hintochallenge.data.dao.PostDao
import com.example.hintochallenge.data.model.Post
import com.example.hintochallenge.data.model.PostEntity
import com.example.hintochallenge.toDomain
import javax.inject.Inject

class RepositoryDbImpl @Inject constructor(
    private val postDao: PostDao
): RepositoryDb {
    override suspend fun insertPost(post: PostEntity) {
        postDao.insertPost(post)
    }

    override suspend fun getAllPosts(): Result<List<Post>> {
        return try {
            val posts = postDao.getAllPosts().map{ it.toDomain() }
            if (posts.isNotEmpty()) {
                Result.success(posts)
            } else {
                Result.failure(Exception("No posts found in DB"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deletePostById(postId: Int) {
        postDao.deletePost(postId)
    }

    override suspend fun checkFavorite(postId: Int): Int {
        return postDao.checkFavorite(postId)
    }
}