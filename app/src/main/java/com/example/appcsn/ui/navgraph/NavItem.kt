package com.example.appcsn.ui.navgraph


import androidx.compose.ui.graphics.vector.ImageVector
import com.ramcosta.composedestinations.spec.Direction
import com.ramcosta.composedestinations.spec.NavGraphSpec

data class NavItem(
    val graph: NavGraphSpec,
    var backStack: MutableList<Direction> = mutableListOf(),
    val index: Int,
    val name: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
    val isNew: Boolean = false,
    val updateNews: Int = 0,
)
