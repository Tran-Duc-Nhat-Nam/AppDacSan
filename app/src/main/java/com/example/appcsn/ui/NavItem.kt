package com.example.appcsn.ui


import androidx.compose.ui.graphics.vector.ImageVector
import com.example.appcsn.screen.destinations.Destination

data class NavItem(
    val destination: Destination,
    val index: Int,
    val name: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
    val isNew: Boolean = false,
    val updateNews: Int = 0,
)
