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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.appcsn.screens.Screen
import com.example.appcsn.screens.TrangChiTietDacSan
import com.example.appcsn.screens.TrangChiTietNoiBan
import com.example.appcsn.screens.TrangChuDacSan
import com.example.appcsn.screens.TrangNoiBan
import com.example.appcsn.ui.theme.AppĐặcSảnTheme

class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppĐặcSảnTheme {
                var searchText by remember {
                    mutableStateOf("")
                }
                var active by remember {
                    mutableStateOf(false)
                }
                LaunchedEffect(key1 = true) {
                    mainViewModel.docDuLieu()
                }
                val navController = rememberNavController()
                Scaffold(
                    topBar = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            SearchBar(
                                query = searchText,
                                onQueryChange = { searchText = it },
                                onSearch = {

                                    active = false
                                },
                                active = active,
                                onActiveChange = {
                                    active = it
                                },
                                placeholder = { Text("Tìm kiếm") },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.Search,
                                        contentDescription = null
                                    )
                                },
                                trailingIcon = {
                                    Icon(
                                        Icons.Default.MoreVert,
                                        contentDescription = null
                                    )
                                },
                            ) {

                            }
                        }
                    },
                    bottomBar = {
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier
                                .height(80.dp)
                                .fillMaxWidth()
                                .background(Color(30, 144, 255))
                        ) {
                            IconButton(
                                onClick = {
                                    navController.navigate(Screen.TrangDacSan.TrangChuDacSan.route)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Home,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                            IconButton(
                                onClick = {
                                    navController.navigate(Screen.TrangNoiBan.TrangChuNoiBan.route)
                                },
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                            IconButton(
                                onClick = {
                                    navController.navigate(
                                        Screen.TrangNguoiDung.route
                                    )
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = innerPadding.calculateBottomPadding())
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = Screen.TrangDacSan.route
                        ) {
                            navigation(
                                route = Screen.TrangDacSan.route,
                                startDestination = Screen.TrangDacSan.TrangChuDacSan.route
                            ) {
                                composable(route = Screen.TrangDacSan.TrangChuDacSan.route) {
                                    TrangChuDacSan(
                                        navController = navController,
                                        dsDacSan = mainViewModel.dsDacSan
                                    )
                                }
                                composable(
                                    route = "${Screen.TrangDacSan.TrangChiTietDacSan.route}/{id}",
                                    arguments = listOf(navArgument("id") {
                                        type = NavType.IntType
                                        nullable = false
                                    })
                                ) {
                                    TrangChiTietDacSan(
                                        idDacSan = it.arguments?.getInt("userId")!!
                                    )
                                }
                            }
                            navigation(
                                route = Screen.TrangNoiBan.route,
                                startDestination = Screen.TrangNoiBan.TrangChuNoiBan.route
                            ) {
                                composable(route = Screen.TrangNoiBan.TrangChuNoiBan.route) {
                                    TrangNoiBan(
                                        navController = navController,
                                        dsNoiBan = mainViewModel.dsNoiBan
                                    )
                                }
                                composable(
                                    route = "${Screen.TrangNoiBan.TrangChiTietNoiBan.route}/{id}",
                                    arguments = listOf(navArgument("id") {
                                        type = NavType.IntType
                                        nullable = false
                                    })
                                ) {
                                    TrangChiTietNoiBan(
                                        idNoiBan = it.arguments?.getInt("userId")!!
                                    )
                                }
                            }
                            composable(route = Screen.TrangDacSan.TrangChuDacSan.route) {
                                TrangChuDacSan(
                                    navController = navController,
                                    dsDacSan = mainViewModel.dsDacSan
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

