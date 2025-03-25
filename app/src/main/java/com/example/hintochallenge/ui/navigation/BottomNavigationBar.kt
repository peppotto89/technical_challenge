package com.example.hintochallenge.ui.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .shadow(8.dp, shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
        containerColor = Color(0xFFCCCCCC),
        tonalElevation = 12.dp
    ) {
        val items = listOf(
            BottomNavItem("All Posts", Icons.Default.Home, Screen.Main.route),
            BottomNavItem("Favorites", Icons.Default.Favorite, Screen.Favorite.route)
        )

        items.forEach { item ->
            val isSelected = item.route == currentRoute
            NavigationBarItem(
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = item.label,
                        tint = if (isSelected) Color(0xFFBDBDBD) else Color.Black
                    )
                },
                label = {
                    Text(
                        item.label,
                        color = if (isSelected) Color(0xFF4D4D4D) else Color.Black,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                selected = isSelected,
                onClick = { navController.navigate(item.route) }
            )
        }
    }
}

data class BottomNavItem(val label: String, val icon: ImageVector, val route: String)