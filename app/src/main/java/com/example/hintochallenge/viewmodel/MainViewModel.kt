package com.example.hintochallenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hintochallenge.data.usecase.GetPostUsecase
import com.example.hintochallenge.data.model.Post
import com.example.hintochallenge.data.usecase.GetFavoritePostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPostsUseCase: GetPostUsecase,
    private val getFavoritePostsUseCase: GetFavoritePostsUseCase
): ViewModel() {
    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> get() = _posts

    private val _errorMsg = MutableStateFlow("")
    val errorMsg: StateFlow<String> get() = _errorMsg

    private val _favoritePosts = MutableStateFlow<List<Post>>(emptyList())
    val favoritePosts: StateFlow<List<Post>> get() = _favoritePosts

    private var _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        getPosts()
    }

    fun getPosts() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = getPostsUseCase.invoke()
            if(result.isSuccess) {
                _posts.value = result.getOrDefault(emptyList())
                _isLoading.value = false
            }else {
                _errorMsg.value = "Unable to retrieve posts...retry"
                _isLoading.value = false
            }
        }
    }

    fun getFavoritePosts(){
        viewModelScope.launch {
            val result = getFavoritePostsUseCase.invoke()
            if(result.isSuccess) {
                _favoritePosts.value = result.getOrDefault(emptyList())
            }else {
                _favoritePosts.value = emptyList()
            }
        }
    }
}