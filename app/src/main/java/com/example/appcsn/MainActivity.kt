package com.example.appcsn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.IconToggleButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.appcsn.models.DanhSachVungMien
import com.example.appcsn.screens.NavGraphs
import com.example.appcsn.screens.TrangChuDacSan
import com.example.appcsn.screens.TrangChuNoiBan
import com.example.appcsn.screens.destinations.TrangChuDacSanDestination
import com.example.appcsn.screens.destinations.TrangChuNoiBanDestination
import com.example.appcsn.screens.destinations.TrangNguoiDungDestination
import com.example.appcsn.screens.destinations.TrangTimKiemDacSanDestination
import com.example.appcsn.ui.theme.AppTheme
import com.example.appcsn.viewmodels.MainViewModel
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.ramcosta.composedestinations.navigation.navigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()

    @OptIn(
        ExperimentalMaterial3Api::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.initAuth()
        var connected = false

        enableEdgeToEdge()
        setContent {
            AppTheme {
                var searchText by remember {
                    mutableStateOf("")
                }
                var active by remember {
                    mutableStateOf(false)
                }
                var pos by remember {
                    mutableIntStateOf(1)
                }
                val navColor = IconToggleButtonColors(
                    checkedContainerColor = Color(65, 105, 225),
                    checkedContentColor = Color(0, 191, 255),
                    containerColor = Color(65, 105, 225),
                    contentColor = Color.White,
                    disabledContainerColor = Color(65, 105, 225),
                    disabledContentColor = Color.Gray
                )
                LaunchedEffect(key1 = true) {
                    connected = mainViewModel.checkConnect()
                    if (connected) {
                        mainViewModel.docDuLieu()
                        mainViewModel.loading.value = false
                    }
                }
                val navController = rememberNavController()
                if (mainViewModel.loading.value) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(
                            strokeWidth = 6.dp,
                            modifier = Modifier.size(85.dp)
                        )
                    }
                } else {
                    Scaffold(
                        topBar = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                SearchBar(
                                    query = searchText,
                                    onQueryChange = { searchText = it },
                                    onSearch = {
                                        navController.navigate(
                                            TrangTimKiemDacSanDestination(
                                                it, DanhSachVungMien()
                                            )
                                        )
                                        active = false
                                    },
                                    active = active,
                                    onActiveChange = {
                                        active = it
                                    },
                                    placeholder = { Text("Tìm kiếm") },
                                    leadingIcon = {
                                        if (active) {
                                            IconButton(onClick = { active = false }) {
                                                Icon(
                                                    Icons.AutoMirrored.Filled.ArrowBack,
                                                    contentDescription = null,
                                                )
                                            }
                                        } else {
                                            Icon(
                                                Icons.Default.Search,
                                                contentDescription = null,
                                            )
                                        }
                                    },
                                    trailingIcon = {
                                        Icon(
                                            Icons.Default.MoreVert,
                                            contentDescription = null,
                                        )
                                    },
                                ) {
                                    LazyColumn(
                                        Modifier
                                            .padding(horizontal = 15.dp, vertical = 10.dp)
                                    ) {
                                        items(mainViewModel.dsDacSan.filter {
                                            it.ten.contains(
                                                searchText
                                            )
                                        }) { dacSan ->
                                            Text(
                                                text = dacSan.ten,
                                            )
                                        }
                                    }
                                }
                            }
                        },
                        bottomBar = {
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                modifier = Modifier
                                    .height(95.dp)
                                    .fillMaxWidth()
                                    .background(Color(65, 105, 225))
                            ) {
                                IconToggleButton(
                                    checked = pos == 1,
                                    onCheckedChange = { checked ->
                                        if (checked) {
                                            navController.navigate(TrangChuDacSanDestination)
                                            pos = 1
                                        }
                                    },
                                    colors = navColor
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Home,
                                        contentDescription = null,
                                    )
                                }
                                IconToggleButton(
                                    checked = pos == 2,
                                    onCheckedChange = { checked ->
                                        if (checked) {
                                            navController.navigate(TrangChuNoiBanDestination)
                                            pos = 2
                                        }
                                    },
                                    colors = navColor
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = null,
                                    )
                                }
                                IconToggleButton(
                                    checked = pos == 3,
                                    onCheckedChange = { checked ->
                                        if (checked) {
                                            navController.navigate(
                                                TrangNguoiDungDestination(
                                                    nguoiDung = null
                                                )
                                            )
                                            pos = 3
                                        }
                                    },
                                    colors = navColor
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = null,
                                    )
                                }
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    ) { innerPadding ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    top = innerPadding.calculateTopPadding() + 10.dp,
                                    bottom = innerPadding.calculateBottomPadding()
                                )
                        ) {
                            DestinationsNavHost(
                                navController = navController,
                                navGraph = NavGraphs.root,
                                startRoute = if (connected) {
                                    TrangChuDacSanDestination
                                } else {
                                    TrangNguoiDungDestination
                                }
                            ) {
                                composable(TrangChuDacSanDestination) {
                                    TrangChuDacSan(
                                        navigator = this.destinationsNavigator,
                                        dsDacSan = mainViewModel.dsDacSan
                                    )
                                }
                                composable(TrangChuNoiBanDestination) {
                                    TrangChuNoiBan(
                                        navigator = this.destinationsNavigator,
                                        dsNoiBan = mainViewModel.dsNoiBan
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
