package com.example.appcsn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.appcsn.data.model.DanhSachMuaDacSan
import com.example.appcsn.data.model.DanhSachNguyenLieu
import com.example.appcsn.data.model.DanhSachVungMien
import com.example.appcsn.screen.NavGraphs
import com.example.appcsn.screen.destinations.TrangChiTietDacSanDestination
import com.example.appcsn.screen.destinations.TrangChuDacSanDestination
import com.example.appcsn.screen.destinations.TrangChuNoiBanDestination
import com.example.appcsn.screen.destinations.TrangNguoiDungDestination
import com.example.appcsn.screen.destinations.TrangTimKiemDacSanDestination
import com.example.appcsn.ui.CircleProgressIndicator
import com.example.appcsn.ui.NavItem
import com.example.appcsn.ui.theme.AppTheme
import com.example.appcsn.viewmodel.MainViewModel
import com.example.appcsn.viewmodel.TrangChuDacSanViewModel
import com.example.appcsn.viewmodel.TrangChuNoiBanViewModel
import com.example.appcsn.viewmodel.TrangNguoiDungViewModel
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.navigation.navigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()
    private val dacSanViewModel by viewModels<TrangChuDacSanViewModel>()
    private val noiBanViewModel by viewModels<TrangChuNoiBanViewModel>()
    private val nguoiDungViewModel by viewModels<TrangNguoiDungViewModel>()
    private val dsNavItem = listOf(
        NavItem(
            destination = TrangChuDacSanDestination,
            index = 1,
            name = "Đặc sản",
            icon = Icons.Outlined.Home,
            selectedIcon = Icons.Default.Home
        ),
        NavItem(
            destination = TrangChuNoiBanDestination,
            index = 2,
            name = "Nơi bán",
            icon = Icons.Outlined.LocationOn,
            selectedIcon = Icons.Default.LocationOn
        ),
        NavItem(
            destination = TrangNguoiDungDestination,
            index = 3,
            name = "Người dùng",
            icon = Icons.Outlined.Person,
            selectedIcon = Icons.Default.Person
        )
    )

    @OptIn(
        ExperimentalMaterial3Api::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.initAuth()

        enableEdgeToEdge()
        setContent {
            AppTheme {
                var connected by remember {
                    mutableStateOf(false)
                }
                var searchText by remember {
                    mutableStateOf("")
                }
                var active by remember {
                    mutableStateOf(false)
                }
                var pos by remember {
                    mutableIntStateOf(1)
                }
                LaunchedEffect(key1 = true) {
                    connected = mainViewModel.checkConnect()
                    nguoiDungViewModel.docNguoiDungFirebase()
                }
                val navController = rememberNavController()
                if (nguoiDungViewModel.loading.value) {
                    Scaffold(Modifier.fillMaxSize()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it)
                        ) {
                            CircleProgressIndicator()
                        }
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
                                    onQueryChange = {
                                        searchText = it
                                        mainViewModel.docDuLieu(it)
                                    },
                                    onSearch = {
                                        navController.navigate(
                                            TrangTimKiemDacSanDestination(
                                                it,
                                                DanhSachVungMien(),
                                                DanhSachMuaDacSan(),
                                                DanhSachNguyenLieu()
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
                                        items(mainViewModel.dsDacSan) { dacSan ->
                                            Text(
                                                text = dacSan.ten,
                                                modifier = Modifier.clickable {
                                                    navController.navigate(
                                                        TrangChiTietDacSanDestination(
                                                            dacSan
                                                        )
                                                    )
                                                    active = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        },
                        bottomBar = {
                            NavigationBar(Modifier.wrapContentHeight()) {
                                dsNavItem.forEach { navItem ->
                                    NavigationBarItem(
                                        selected = pos == navItem.index,
                                        onClick = {
                                            navController.navigate(navItem.destination.route)
                                            pos = navItem.index
                                        },
                                        icon = {
                                            Icon(
                                                imageVector = if (pos == navItem.index) navItem.selectedIcon else navItem.icon,
                                                contentDescription = navItem.name,
                                                modifier = Modifier
                                                    .size(22.dp)
                                            )
                                        },
                                        label = { Text(text = navItem.name, fontSize = 15.sp) },
                                    )
                                }
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    ) { innerPadding ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(innerPadding)
                        ) {
                            DestinationsNavHost(
                                navController = navController,
                                navGraph = NavGraphs.root,
                                startRoute = if (connected) {
                                    TrangChuDacSanDestination
                                } else {
                                    TrangNguoiDungDestination
                                },
                                dependenciesContainerBuilder = {
                                    dependency(TrangChuDacSanDestination) {
                                        dacSanViewModel
                                    }
                                    dependency(TrangChuNoiBanDestination) {
                                        noiBanViewModel
                                    }
                                    dependency(TrangNguoiDungDestination) {
                                        nguoiDungViewModel
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
