package com.example.hintochallenge

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hintochallenge.data.dao.PostDao
import com.example.hintochallenge.data.model.PostEntity

@Database(entities = [PostEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun postDao(): PostDao
}