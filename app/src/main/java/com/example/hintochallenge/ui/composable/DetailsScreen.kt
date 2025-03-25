package com.example.hintochallenge.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.hintochallenge.data.model.Post
import com.example.hintochallenge.viewmodel.DetailsViewModel

@Composable
fun DetailsScreen(navController: NavController, viewModel: DetailsViewModel = hiltViewModel()) {
    val isFavorite by viewModel.isFavorite.collectAsState()
    val post by rememberSaveable { mutableStateOf(navController.previousBackStackEntry
        ?.savedStateHandle
        ?.get<Post>("post")) }

    LaunchedEffect(Unit) {
        post?.id?.let { viewModel.checkFavorite(it) }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(12.dp, 0.dp, 12.dp, 0.dp)) {
        Text(
            text = "Post Detail",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF5D5D5D),
            modifier = Modifier.padding(bottom = 12.dp)
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .clip(RoundedCornerShape(12.dp)),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF969696))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFF818181), Color(0xFF5B5B5B))
                        )
                    )
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .verticalScroll(rememberScrollState())
                        .padding(20.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = post?.title?.replaceFirstChar { it.uppercase() } ?: "",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFE0E0E0),
                            modifier = Modifier.weight(1f),
                            style = TextStyle(
                                shadow = Shadow(
                                    color = Color.Black,
                                    offset = Offset(2f, 2f),
                                    blurRadius = 4f
                                )
                            )
                        )

                        IconButton(onClick = { post?.let { viewModel.toggleFavorite(it) } }) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = "Add to favorites",
                                    tint = Color.Red
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = post?.body?.replaceFirstChar { it.uppercase() } ?: "",
                        fontSize = 18.sp,
                        color = Color(0xFFCFCFCF)
                    )
                }
            }
        }
    }
}
