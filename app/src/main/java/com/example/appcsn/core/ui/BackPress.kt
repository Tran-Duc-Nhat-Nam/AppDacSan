package com.example.appcsn.core.ui

import androidx.navigation.NavController
import com.example.appcsn.core.ui.viewmodel.BaseViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.spec.Direction

fun back(
    navigator: DestinationsNavigator,
    id: Int,
) {
    BaseViewModel.dsNavItem[id].backStack.removeLast()
    navigator.navigate(BaseViewModel.dsNavItem[id].backStack.last())
}

fun back(
    navController: NavController,
    id: Int,
) {
    BaseViewModel.dsNavItem[id].backStack.removeLast()
    navController.navigate(BaseViewModel.dsNavItem[id].backStack.last())
}

fun go(
    navigator: DestinationsNavigator,
    direction: Direction,
    id: Int
) {
    BaseViewModel.dsNavItem[id].backStack.add(
        direction
    )
    navigator.navigate(BaseViewModel.dsNavItem[id].backStack.last())
}

fun go(
    navController: NavController,
    direction: Direction,
    id: Int
) {
    BaseViewModel.dsNavItem[id].backStack.add(
        direction
    )
    navController.navigate(BaseViewModel.dsNavItem[id].backStack.last())
}