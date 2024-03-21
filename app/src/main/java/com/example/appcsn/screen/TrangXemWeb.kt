package com.example.appcsn.screen

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.appcsn.ui.navgraph.SettingGraph
import com.example.appcsn.ui.widget.PageHeader
import com.example.appcsn.viewmodel.BaseViewModel
import com.kevinnzou.web.WebView
import com.kevinnzou.web.rememberWebViewState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<SettingGraph>()
@Composable
fun TrangXemWeb(
    navigator: DestinationsNavigator,
) {
    val state = rememberWebViewState("https://google.com")

    BackHandler {
        BaseViewModel.dsNavItem[2].backStack.removeLast()
        navigator.navigate(BaseViewModel.dsNavItem[2].backStack.last())
    }

    Column {
        PageHeader("Web")
        WebView(
            state = state,
            onCreated = {
                it.settings.javaScriptEnabled = true
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    it.settings.safeBrowsingEnabled = true
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.settings.isAlgorithmicDarkeningAllowed = true
                }
            },
        )
    }
}