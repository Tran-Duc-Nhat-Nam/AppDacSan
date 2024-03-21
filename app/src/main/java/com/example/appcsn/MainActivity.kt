package com.example.appcsn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.appcsn.ui.theme.AppTheme
import com.example.appcsn.ui.widget.CircleProgressIndicator
import com.example.appcsn.viewmodel.BaseViewModel.Companion.dataStore
import com.example.appcsn.viewmodel.BaseViewModel.Companion.dsNavItem
import com.example.appcsn.viewmodel.MainViewModel
import com.example.appcsn.viewmodel.TrangChiTietDacSanViewModel
import com.example.appcsn.viewmodel.TrangChuDacSanViewModel
import com.example.appcsn.viewmodel.TrangChuNoiBanViewModel
import com.example.appcsn.viewmodel.TrangNguoiDungViewModel
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.generated.destinations.TrangBaoLoiDestination
import com.ramcosta.composedestinations.generated.destinations.TrangCaiDatGiaoDienDestination
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
import compose.icons.CssGgIcons
import compose.icons.cssggicons.Bot
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()
    private val dacSanViewModel by viewModels<TrangChuDacSanViewModel>()
    private val noiBanViewModel by viewModels<TrangChuNoiBanViewModel>()
    private val nguoiDungViewModel by viewModels<TrangNguoiDungViewModel>()
    private val chiTietDacSanViewModel by viewModels<TrangChiTietDacSanViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.initAuth()

        val key = intPreferencesKey("brightness")
        val brightnessFlow: Flow<Int> = dataStore.data
            .map { preferences ->
                preferences[key] ?: 0
            }

        enableEdgeToEdge()
        setContent {
            val coroutine = rememberCoroutineScope()
            val context = LocalContext.current

            var brightness by remember {
                mutableIntStateOf(0)
            }

            LaunchedEffect(Unit) {
                brightnessFlow.collect {
                    brightness = it
                }
            }

            AppTheme(
                useDarkTheme = when {
                    (brightness == 1) -> false
                    (brightness == 2) -> true
                    else -> isSystemInDarkTheme()
                }
            ) {
                val connected = remember {
                    mutableStateOf(false)
                }
                val tenTimKiem = remember {
                    mutableStateOf("")
                }
                val tenNguyenLieu = remember {
                    mutableStateOf("")
                }
                val isChonNguyenLieu = remember {
                    mutableStateOf(false)
                }
                val active = remember {
                    mutableStateOf(false)
                }
                val filter = remember {
                    mutableStateOf(false)
                }
                val chatBot = remember {
                    mutableStateOf(false)
                }
                val chatBotInput = remember {
                    mutableStateOf("")
                }
                val chatLoad = remember {
                    mutableStateOf(false)
                }
                val pos = remember {
                    mutableIntStateOf(1)
                }

                val chatState = rememberLazyListState()
                val focusManager = LocalFocusManager.current
                val navController = rememberNavController()

                LaunchedEffect(Unit) {
                    connected.value = mainViewModel.checkConnect()
                    nguoiDungViewModel.docNguoiDungFirebase()
                }

                MaterialTheme {
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
                                TopBar(tenTimKiem, pos, active, navController, filter, chatBot)
                            },
                            bottomBar = {
                                BottomBar(pos, navController)
                            },
                            modifier = Modifier.fillMaxSize()
                        ) { innerPadding ->
                            FilterDialog(filter, isChonNguyenLieu, tenNguyenLieu)
                            ChatBotDialog(
                                chatBot,
                                chatBotInput,
                                coroutine,
                                focusManager,
                                chatLoad,
                                mainViewModel.chatInputs,
                                chatState,
                                mainViewModel.chatOutputs,
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(innerPadding)
                            ) {
                                NavHost(navController, connected)
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun TopBar(
        tenTimKiem: MutableState<String>,
        pos: MutableIntState,
        active: MutableState<Boolean>,
        navController: NavHostController,
        filter: MutableState<Boolean>,
        chatBot: MutableState<Boolean>
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceContainer)
        ) {
            SearchBar(
                query = tenTimKiem.value,
                onQueryChange = {
                    goiY(tenTimKiem, it, pos)
                },
                onSearch = {
                    timKiem(it, active, pos, navController)
                },
                active = active.value,
                onActiveChange = {
                    active.value = it
                },
                placeholder = {
                    Text(
                        "Tìm kiếm ${
                            when {
                                (pos.intValue == 1) -> "đặc sản"
                                (pos.intValue == 2) -> "nơi bán"
                                else -> "người dùng"
                            }
                        }"
                    )
                },
                leadingIcon = {
                    IconDauThanhTimKiem(active)
                },
                trailingIcon = {
                    IconCuoiThanhTimKiem(active, filter, chatBot)
                },
            ) {
                ManHinhTimKiem(pos, navController, active)
            }
            Spacer(modifier = Modifier.height(15.dp))
        }
    }

    @Composable
    private fun IconCuoiThanhTimKiem(
        active: MutableState<Boolean>,
        filter: MutableState<Boolean>,
        chatBot: MutableState<Boolean>
    ) {
        if (active.value) {
            IconButton(onClick = { filter.value = !filter.value }) {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = null,
                )
            }
        } else {
            IconButton(
                onClick = { chatBot.value = true },
            ) {
                Icon(
                    imageVector = CssGgIcons.Bot,
                    contentDescription = "Gemini chat bot"
                )
            }
        }
    }

    @Composable
    private fun IconDauThanhTimKiem(active: MutableState<Boolean>) {
        if (active.value) {
            IconButton(onClick = { active.value = false }) {
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
    }

    private fun goiY(
        tenTimKiem: MutableState<String>,
        it: String,
        pos: MutableIntState
    ) {
        tenTimKiem.value = it
        when (pos.intValue) {
            1 -> {
                mainViewModel.goiYDacSan(it)
            }

            2 -> {
                mainViewModel.goiYNoiBan(it)
            }

            else -> {
                mainViewModel.goiYNguoiDung(it)
            }
        }
    }

    private fun timKiem(
        it: String,
        active: MutableState<Boolean>,
        pos: MutableIntState,
        navController: NavHostController
    ) {
        if (it.isNotBlank()) {
            active.value = false
            when (pos.intValue) {
                1 -> {
                    dsNavItem[0].backStack.add(
                        TrangTimKiemDacSanDestination(
                            it,
                            mainViewModel.taoTuKhoa()
                        )
                    )
                    navController.navigate(
                        dsNavItem[0].backStack.last()
                    )
                }

                2 -> {
                    dsNavItem[1].backStack.add(
                        TrangTimKiemNoiBanDestination(it)
                    )
                    navController.navigate(
                        dsNavItem[1].backStack.last()
                    )
                }
            }
            active.value = false
        }
    }

    @Composable
    private fun ManHinhTimKiem(
        pos: MutableIntState,
        navController: NavHostController,
        active: MutableState<Boolean>
    ) {
        LazyColumn(
            Modifier
                .padding(horizontal = 15.dp, vertical = 10.dp)
        ) {
            when (pos.intValue) {
                1 -> {
                    items(mainViewModel.dsDacSan) { dacSan ->
                        Text(
                            text = dacSan.ten,
                            modifier = Modifier.clickable {
                                dsNavItem[0].backStack.add(
                                    TrangChiTietDacSanDestination(dacSan.id)
                                )
                                navController.navigate(
                                    dsNavItem[0].backStack.last()
                                )
                                active.value = false
                            }
                        )
                    }
                }

                2 -> {
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
                                active.value = false
                            }
                        )
                    }
                }

                else -> {
                    items(mainViewModel.dsNguoiDung) { nguoiDung ->
                        Text(
                            text = nguoiDung.ten,
                            modifier = Modifier.clickable {
                                active.value = false
                            }
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun ChatBotDialog(
        chatBot: MutableState<Boolean>,
        chatBotInput: MutableState<String>,
        coroutine: CoroutineScope,
        focusManager: FocusManager,
        chatLoad: MutableState<Boolean>,
        chatInputs: SnapshotStateList<String>,
        chatState: LazyListState,
        chatOutputs: SnapshotStateList<String>,
    ) {
        if (chatBot.value) {
            Dialog(
                onDismissRequest = { chatBot.value = false },
                properties = DialogProperties()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight(0.85F)
                        .fillMaxWidth()
                ) {
                    TextField(
                        value = chatBotInput.value,
                        onValueChange = { chatBotInput.value = it },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(25.dp),
                        visualTransformation = VisualTransformation.None,
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    coroutine.launch {
                                        chatLoad.value = true
                                        chatInputs.add(chatBotInput.value)
                                        chatBotInput.value = ""
                                        chatState.animateScrollToItem(
                                            chatInputs.lastIndex
                                        )
                                        mainViewModel.chat()
                                        chatState.animateScrollToItem(
                                            chatOutputs.lastIndex
                                        )
                                        chatLoad.value = false
                                    }
                                }) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    LazyColumn(
                        state = chatState,
                        modifier = Modifier
                            .clip(RoundedCornerShape(15.dp))
                            .padding(vertical = 10.dp)
                    ) {
                        items(chatInputs) {
                            ChatItem(it, chatInputs, chatLoad, chatOutputs)
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun MainActivity.ChatItem(
        it: String,
        chatInputs: SnapshotStateList<String>,
        chatLoad: MutableState<Boolean>,
        chatOutputs: SnapshotStateList<String>
    ) {
        InputChat(it)
        if (it == chatInputs.last() && chatLoad.value) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp)
            ) {
                CircleProgressIndicator(
                    size = 25.dp,
                    strokeWidth = 2.dp,
                    innerPadding = PaddingValues(5.dp),
                    backgroundColor = MaterialTheme.colorScheme.surfaceContainer
                )
            }
        } else {
            OutputChat(chatOutputs, chatInputs, it)
        }
    }

    @Composable
    private fun OutputChat(
        chatOutputs: SnapshotStateList<String>,
        chatInputs: SnapshotStateList<String>,
        it: String
    ) {
        if (chatOutputs.size > chatInputs.indexOf(it)) {
            Text(
                text = chatOutputs[chatInputs.indexOf(it)],
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(10.dp)
            )
        }
    }

    @Composable
    private fun InputChat(it: String) {
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = it,
                textAlign = TextAlign.End,
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(10.dp)
            )
        }
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun FilterDialog(
        filter: MutableState<Boolean>,
        isChonNguyenLieu: MutableState<Boolean>,
        tenNguyenLieu: MutableState<String>
    ) {
        if (filter.value) {
            Dialog(
                onDismissRequest = { filter.value = false },
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
                        expanded = isChonNguyenLieu.value,
                        onExpandedChange = {
                            isChonNguyenLieu.value = !isChonNguyenLieu.value
                        }
                    ) {
                        OutlinedTextField(
                            value = tenNguyenLieu.value,
                            onValueChange = {
                                tenNguyenLieu.value = it
                                mainViewModel.goiYNguyenLieu(it)
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = isChonNguyenLieu.value
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(25.dp))
                                .padding(10.dp)
                                .menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = isChonNguyenLieu.value,
                            onDismissRequest = {
                                isChonNguyenLieu.value = false
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
                                        isChonNguyenLieu.value = false
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
    }

    @Composable
    private fun NavHost(
        navController: NavHostController,
        connected: MutableState<Boolean>
    ) {
        DestinationsNavHost(
            navController = navController,
            navGraph = NavGraphs.root,
            startRoute = if (connected.value) {
                NavGraphs.root.startRoute
            } else {
                TrangBaoLoiDestination
            },
            dependenciesContainerBuilder = {
                destination(TrangBaoLoiDestination) {
                    dependency("Không có kết nối mạng")
                }
                destination(TrangChuDacSanDestination) {
                    dependency(dacSanViewModel)
                }
                destination(TrangChuNoiBanDestination) {
                    dependency(noiBanViewModel)
                }
                destination(TrangNguoiDungDestination) {
                    dependency(nguoiDungViewModel)
                }
                destination(TrangCaiDatGiaoDienDestination) {
                    dependency(dataStore)
                }
                destination(TrangChiTietDacSanDestination) {
                    dependency(chiTietDacSanViewModel)
                }
            }
        )
    }

    @Composable
    private fun BottomBar(pos: MutableIntState, navController: NavHostController) {
        NavigationBar(Modifier.wrapContentHeight()) {
            dsNavItem.forEach { navItem ->
                NavigationBarItem(
                    selected = pos.intValue == navItem.index,
                    onClick = {
                        navController.navigate(navItem.backStack.last()) {
                            launchSingleTop = true
                        }
                        pos.intValue = navItem.index
                    },
                    icon = {
                        Icon(
                            imageVector = if (pos.intValue == navItem.index) navItem.selectedIcon else navItem.icon,
                            contentDescription = navItem.name,
                            modifier = Modifier
                                .size(22.dp)
                        )
                    },
                    label = { Text(text = navItem.name, fontSize = 15.sp) },
                )
            }
        }
    }
}
