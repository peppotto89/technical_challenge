package com.example.hintochallenge.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.hintochallenge.viewmodel.MainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel(), navController: NavHostController) {
    val posts by viewModel.posts.collectAsState(initial = emptyList())
    val errorMsg by viewModel.errorMsg.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    val filteredPosts = posts.filter { it.title.contains(searchQuery, ignoreCase = true) }
    val isLoading by viewModel.isLoading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp, 0.dp, 12.dp, 0.dp)) {
        Text(
            text = "Latest Posts",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF5D5D5D),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .shadow(8.dp, shape = RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color(0xFF757575)
                )
            },
            placeholder = {
                Text(
                    "Search posts...",
                    color = Color(0xFF9E9E9E),
                    fontStyle = FontStyle.Italic
                )
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0xFFE0E0E0),
                focusedContainerColor = Color(0xFFD6D6D6),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Black
            )
        )
        if(isLoading){
            CircularProgressIndicator(
                modifier = Modifier.size(40.dp),
                strokeWidth = 8.dp
            )
        }else {
            if (filteredPosts.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredPosts, key = { it.id }) { post ->
                        PostItem(post) {
                            navController.currentBackStackEntry?.savedStateHandle?.set("post", post)
                            navController.navigate("details")
                        }
                    }
                }
            } else if (errorMsg.isEmpty()) {
                Text(text = "No posts found...", modifier = Modifier.padding(bottom = 16.dp))
            } else {
                Text(text = errorMsg, modifier = Modifier.padding(bottom = 16.dp))
                RefreshButton { viewModel.getPosts() }
            }
        }
    }
}

@Composable
fun RefreshButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(Color(0xFF565656))
    ) {
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = "Reload",
            tint = Color.White
        )
    }
}


