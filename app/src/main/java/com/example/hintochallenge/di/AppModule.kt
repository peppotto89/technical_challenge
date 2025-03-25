package com.example.hintochallenge.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.hintochallenge.AppDatabase
import com.example.hintochallenge.data.api.ApiService
import com.example.hintochallenge.data.dao.PostDao
import com.example.hintochallenge.data.usecase.GetPostUsecase
import com.example.hintochallenge.data.repositories.Repository
import com.example.hintochallenge.data.repositories.RepositoryDb
import com.example.hintochallenge.data.repositories.RepositoryDbImpl
import com.example.hintochallenge.data.repositories.RepositoryImpl
import com.example.hintochallenge.data.usecase.CheckFavoriteUseCase
import com.example.hintochallenge.data.usecase.DeletePostUseCase
import com.example.hintochallenge.data.usecase.GetFavoritePostsUseCase
import com.example.hintochallenge.data.usecase.InsertFavoritePostUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    fun provideGetUserUseCase(userRepository: Repository): GetPostUsecase {
        return GetPostUsecase(userRepository)
    }
    @Provides
    fun provideRepository(apiService: ApiService): Repository {
        return RepositoryImpl(apiService)
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "my_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providePostDao(database: AppDatabase): PostDao {
        return database.postDao()
    }

    @Provides
    fun provideInsertFavoritePostUseCase(repository: RepositoryDb): InsertFavoritePostUseCase {
        return InsertFavoritePostUseCase(repository)
    }

    @Provides
    fun provideRepositoryDb(postDao: PostDao): RepositoryDb {
        return RepositoryDbImpl(postDao)
    }

    @Provides
    fun provideDeletePostUseCase(repository: RepositoryDb): DeletePostUseCase {
        return DeletePostUseCase(repository)
    }

    @Provides
    fun provideGetFavoritePostsUseCase(repository: RepositoryDb): GetFavoritePostsUseCase {
        return GetFavoritePostsUseCase(repository)
    }

    @Provides
    fun provideCheckFavoriteUseCase(repository: RepositoryDb): CheckFavoriteUseCase {
        return CheckFavoriteUseCase(repository)
    }

}