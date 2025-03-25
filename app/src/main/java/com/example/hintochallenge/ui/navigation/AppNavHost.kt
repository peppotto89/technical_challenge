package com.example.hintochallenge.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hintochallenge.ui.composable.MainScreen
import com.example.hintochallenge.viewmodel.MainViewModel
import com.example.hintochallenge.ui.composable.DetailsScreen
import com.example.hintochallenge.ui.composable.FavoriteScreen

sealed class Screen(val route: String) {
    data object Main : Screen("main")
    data object Favorite : Screen("favoritePosts")
    data object Details : Screen("details")
}

@Composable
fun AppNavHost (startDestination: String = "main", navController: NavHostController, mainViewModel: MainViewModel = hiltViewModel()){
    Scaffold(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.tertiary),
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        NavHost(navController = navController, startDestination = startDestination, modifier = Modifier.padding(paddingValues))
        {
            composable(Screen.Main.route) {
                MainScreen(mainViewModel, navController)
            }
            composable(Screen.Details.route) {
                DetailsScreen(navController)
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen(mainViewModel, navController)
            }
        }
    }
}