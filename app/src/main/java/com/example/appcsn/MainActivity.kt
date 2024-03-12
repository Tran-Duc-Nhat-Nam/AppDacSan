package com.example.appcsn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AssistChip
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.compose.rememberNavController
import com.example.appcsn.ui.CircleProgressIndicator
import com.example.appcsn.ui.theme.AppTheme
import com.example.appcsn.viewmodel.BaseViewModel.Companion.dsNavItem
import com.example.appcsn.viewmodel.MainViewModel
import com.example.appcsn.viewmodel.TrangChuDacSanViewModel
import com.example.appcsn.viewmodel.TrangChuNoiBanViewModel
import com.example.appcsn.viewmodel.TrangNguoiDungViewModel
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.generated.destinations.TrangChiTietDacSanDestination
import com.ramcosta.composedestinations.generated.destinations.TrangChiTietNoiBanDestination
import com.ramcosta.composedestinations.generated.destinations.TrangChuDacSanDestination
import com.ramcosta.composedestinations.generated.destinations.TrangChuNoiBanDestination
import com.ramcosta.composedestinations.generated.destinations.TrangNguoiDungDestination
import com.ramcosta.composedestinations.generated.destinations.TrangTimKiemDacSanDestination
import com.ramcosta.composedestinations.generated.destinations.TrangTimKiemNoiBanDestination
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.navigation.destination
import com.ramcosta.composedestinations.navigation.navigate
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalLayoutApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()
    private val dacSanViewModel by viewModels<TrangChuDacSanViewModel>()
    private val noiBanViewModel by viewModels<TrangChuNoiBanViewModel>()
    private val nguoiDungViewModel by viewModels<TrangNguoiDungViewModel>()

    @OptIn(
        ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
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
                var tenTimKiem by remember {
                    mutableStateOf("")
                }
                var tenNguyenLieu by remember {
                    mutableStateOf("")
                }
                var isChonNguyenLieu by remember {
                    mutableStateOf(false)
                }
                var active by remember {
                    mutableStateOf(false)
                }
                var filter by remember {
                    mutableStateOf(false)
                }
                var pos by remember {
                    mutableIntStateOf(1)
                }

                val navController = rememberNavController()

                LaunchedEffect(key1 = true) {
                    connected = mainViewModel.checkConnect()
                    nguoiDungViewModel.docNguoiDungFirebase()
                }

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
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                SearchBar(
                                    query = tenTimKiem,
                                    onQueryChange = {
                                        tenTimKiem = it
                                        if (pos == 1) {
                                            mainViewModel.goiYDacSan(it)
                                        } else if (pos == 2) {
                                            mainViewModel.goiYNoiBan(it)
                                        } else {
                                            mainViewModel.goiYNguoiDung(it)
                                        }
                                    },
                                    onSearch = {
                                        if (it.isNotBlank()) {
                                            if (pos == 1) {
                                                dsNavItem[0].backStack.add(
                                                    TrangTimKiemDacSanDestination(
                                                        it,
                                                        mainViewModel.taoTuKhoa()
                                                    )
                                                )
                                                navController.navigate(
                                                    dsNavItem[0].backStack.last()
                                                )
                                            } else if (pos == 2) {
                                                dsNavItem[1].backStack.add(
                                                    TrangTimKiemNoiBanDestination(it)
                                                )
                                                navController.navigate(
                                                    dsNavItem[1].backStack.last()
                                                )
                                            }
                                            active = false
                                        }
                                    },
                                    active = active,
                                    onActiveChange = {
                                        active = it
                                    },
                                    placeholder = {
                                        Text(
                                            "Tìm kiếm ${
                                                when {
                                                    (pos == 1) -> "đặc sản"
                                                    (pos == 2) -> "nơi bán"
                                                    else -> "người dùng"
                                                }
                                            }"
                                        )
                                    },
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
                                        if (active) {
                                            IconButton(onClick = { filter = !filter }) {
                                                Icon(
                                                    Icons.Default.Settings,
                                                    contentDescription = null,
                                                )
                                            }
                                        }
                                    },
                                ) {
                                    LazyColumn(
                                        Modifier
                                            .padding(horizontal = 15.dp, vertical = 10.dp)
                                    ) {
                                        if (pos == 1) {
                                            items(mainViewModel.dsDacSan) { dacSan ->
                                                Text(
                                                    text = dacSan.ten,
                                                    modifier = Modifier.clickable {
                                                        dsNavItem[0].backStack.add(
                                                            TrangChiTietDacSanDestination(dacSan)
                                                        )
                                                        navController.navigate(
                                                            dsNavItem[0].backStack.last()
                                                        )
                                                        active = false
                                                    }
                                                )
                                            }
                                        } else if (pos == 2) {
                                            items(mainViewModel.dsNoiBan) { noiBan ->
                                                Text(
                                                    text = noiBan.ten,
                                                    modifier = Modifier.clickable {
                                                        dsNavItem[1].backStack.add(
                                                            TrangChiTietNoiBanDestination(noiBan)
                                                        )
                                                        navController.navigate(
                                                            dsNavItem[1].backStack.last()
                                                        )
                                                        active = false
                                                    }
                                                )
                                            }
                                        } else {
                                            items(mainViewModel.dsNguoiDung) { nguoiDung ->
                                                Text(
                                                    text = nguoiDung.ten,
                                                    modifier = Modifier.clickable {
                                                        active = false
                                                    }
                                                )
                                            }
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
                                            navController.navigate(navItem.backStack.last()) {
                                                launchSingleTop = true
                                            }
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
                        if (filter) {
                            Dialog(
                                onDismissRequest = { filter = false },
                                properties = DialogProperties()
                            ) {
                                Column(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(MaterialTheme.colorScheme.surfaceContainer)
                                ) {
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        text = "Vùng miền",
                                        modifier = Modifier
                                            .padding(start = 15.dp)
                                    )
                                    FlowRow(modifier = Modifier.padding(10.dp)) {
                                        mainViewModel.dsVungMien.forEach { vungMien ->
                                            ElevatedFilterChip(
                                                selected = mainViewModel.dsChonVungMien[mainViewModel.dsVungMien.indexOf(
                                                    vungMien
                                                )],
                                                onClick = {
                                                    mainViewModel.dsChonVungMien[mainViewModel.dsVungMien.indexOf(
                                                        vungMien
                                                    )] =
                                                        !mainViewModel.dsChonVungMien[mainViewModel.dsVungMien.indexOf(
                                                            vungMien
                                                        )]
                                                },
                                                label = {
                                                    Text(
                                                        text = vungMien.ten,
                                                    )
                                                },
                                                modifier = Modifier.padding(end = 10.dp),
                                            )
                                        }
                                    }
                                    HorizontalDivider()
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        text = "Mùa",
                                        modifier = Modifier
                                            .padding(start = 15.dp)
                                    )
                                    FlowRow(modifier = Modifier.padding(10.dp)) {
                                        mainViewModel.dsMuaDacSan.forEach { muaDacSan ->
                                            ElevatedFilterChip(
                                                selected = mainViewModel.dsChonMuaDacSan[mainViewModel.dsMuaDacSan.indexOf(
                                                    muaDacSan
                                                )],
                                                onClick = {
                                                    mainViewModel.dsChonMuaDacSan[mainViewModel.dsMuaDacSan.indexOf(
                                                        muaDacSan
                                                    )] =
                                                        !mainViewModel.dsChonMuaDacSan[mainViewModel.dsMuaDacSan.indexOf(
                                                            muaDacSan
                                                        )]
                                                },
                                                label = {
                                                    Text(
                                                        text = muaDacSan.ten,
                                                    )
                                                },
                                                modifier = Modifier.padding(end = 10.dp),
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        text = "Nguyên liệu",
                                        modifier = Modifier
                                            .padding(start = 15.dp)
                                    )
                                    ExposedDropdownMenuBox(
                                        expanded = isChonNguyenLieu,
                                        onExpandedChange = {
                                            isChonNguyenLieu = !isChonNguyenLieu
                                        }
                                    ) {
                                        OutlinedTextField(
                                            value = tenNguyenLieu,
                                            onValueChange = {
                                                tenNguyenLieu = it
                                                mainViewModel.goiYNguyenLieu(it)
                                            },
                                            trailingIcon = {
                                                ExposedDropdownMenuDefaults.TrailingIcon(
                                                    expanded = isChonNguyenLieu
                                                )
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clip(RoundedCornerShape(25.dp))
                                                .padding(10.dp)
                                                .menuAnchor()
                                        )
                                        ExposedDropdownMenu(
                                            expanded = isChonNguyenLieu,
                                            onDismissRequest = {
                                                isChonNguyenLieu = false
                                            },
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(25.dp))
                                                .padding(10.dp)
                                        ) {
                                            mainViewModel.dsNguyenLieu.forEach { nguyenLieu ->
                                                DropdownMenuItem(
                                                    text = { Text(text = nguyenLieu.ten) },
                                                    onClick = {
                                                        mainViewModel.dsNguyenLieuDaChon.add(
                                                            nguyenLieu
                                                        )
                                                        isChonNguyenLieu = false
                                                    })
                                            }
                                        }
                                    }
                                    FlowRow(modifier = Modifier.padding(10.dp)) {
                                        mainViewModel.dsNguyenLieuDaChon.forEach { nguyenLieu ->
                                            AssistChip(
                                                onClick = {
                                                    mainViewModel.dsNguyenLieuDaChon.remove(
                                                        nguyenLieu
                                                    )
                                                },
                                                label = {
                                                    Text(
                                                        text = nguyenLieu.ten,
                                                    )
                                                    Spacer(modifier = Modifier.weight(1F))
                                                    Icon(
                                                        imageVector = Icons.Default.Close,
                                                        contentDescription = "Bỏ từ khóa ${nguyenLieu.ten}"
                                                    )
                                                },
                                                modifier = Modifier.padding(end = 10.dp),
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(innerPadding)
                        ) {
                            DestinationsNavHost(
                                navController = navController,
                                navGraph = NavGraphs.root,
                                startRoute = if (connected) {
                                    NavGraphs.root.startRoute
                                } else {
                                    TrangNguoiDungDestination
                                },
                                dependenciesContainerBuilder = {
                                    destination(TrangChuDacSanDestination) {
                                        dependency(dacSanViewModel)
                                    }
                                    destination(TrangChuNoiBanDestination) {
                                        dependency(noiBanViewModel)
                                    }
                                    destination(TrangNguoiDungDestination) {
                                        dependency(nguoiDungViewModel)
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
