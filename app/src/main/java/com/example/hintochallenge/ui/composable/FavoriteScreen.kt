package com.example.hintochallenge.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.hintochallenge.viewmodel.MainViewModel

@Composable
fun FavoriteScreen(viewModel: MainViewModel, navController: NavHostController) {
    val posts by viewModel.favoritePosts.collectAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        viewModel.getFavoritePosts()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Favorite Posts",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF5D5D5D),
            modifier = Modifier.padding(bottom = 12.dp)
        )
        if(posts.isEmpty()){
            Text(text = "No favorite posts", modifier = Modifier.padding(bottom = 16.dp))
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(posts, key = { it.id }) { post ->
                    PostItem(post) {
                        navController.currentBackStackEntry?.savedStateHandle?.set("post", post)
                        navController.navigate("details")
                    }
                }
            }
        }
    }
}