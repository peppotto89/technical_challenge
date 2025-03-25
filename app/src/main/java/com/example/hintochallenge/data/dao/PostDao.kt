package com.example.hintochallenge.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hintochallenge.data.model.PostEntity

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPost(post: PostEntity)

    @Query("SELECT * FROM posts")
    suspend fun getAllPosts(): List<PostEntity>

    @Query("DELETE FROM posts WHERE id = :postId")
    suspend fun deletePost(postId: Int)

    @Query("SELECT COUNT(*) FROM posts WHERE id = :postId")
    suspend fun checkFavorite(postId: Int): Int
}