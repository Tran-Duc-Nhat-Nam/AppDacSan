package com.example.appcsn.core.ui

import androidx.navigation.NavController
import com.example.appcsn.core.ui.viewmodel.BaseViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.navigate

fun backPress(
    navigator: DestinationsNavigator,
    id: Int,
) {
    BaseViewModel.dsNavItem[id].backStack.removeLast()
    navigator.navigate(BaseViewModel.dsNavItem[id].backStack.last())
}

fun backPress(
    navController: NavController,
    id: Int,
) {
    BaseViewModel.dsNavItem[id].backStack.removeLast()
    navController.navigate(BaseViewModel.dsNavItem[id].backStack.last())
}