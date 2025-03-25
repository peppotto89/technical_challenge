package com.example.hintochallenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hintochallenge.data.model.Post
import com.example.hintochallenge.data.usecase.CheckFavoriteUseCase
import com.example.hintochallenge.data.usecase.DeletePostUseCase
import com.example.hintochallenge.data.usecase.InsertFavoritePostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val insertFavoritePostUseCase: InsertFavoritePostUseCase,
    private val checkFavoriteUseCase: CheckFavoriteUseCase,
    private val deletePostUseCase: DeletePostUseCase
    ): ViewModel() {

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    fun checkFavorite(postId: Int) {
        viewModelScope.launch {
            _isFavorite.value = checkFavoriteUseCase.invoke(postId)
        }
    }

    fun toggleFavorite(post: Post) {
        if (_isFavorite.value) {
            deletePostById(post.id)
        } else {
            addFavorite(post)
        }
        _isFavorite.value = !_isFavorite.value
    }

    private fun addFavorite(post: Post){
        viewModelScope.launch {
            runCatching {
                insertFavoritePostUseCase.invoke(post)
            }.getOrElse { e -> e.printStackTrace() }
        }
    }

    private fun deletePostById(postId: Int) {
        viewModelScope.launch {
            runCatching {
                deletePostUseCase.invoke(postId)
            }.getOrElse { e -> e.printStackTrace() }
        }
    }
}