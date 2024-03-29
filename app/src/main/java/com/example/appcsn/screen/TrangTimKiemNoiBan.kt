package com.example.appcsn.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appcsn.ui.navgraph.PlaceGraph
import com.example.appcsn.ui.widget.CircleProgressIndicator
import com.example.appcsn.viewmodel.BaseViewModel
import com.example.appcsn.viewmodel.TrangTimKiemNoiBanViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.generated.destinations.TrangChiTietNoiBanDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlin.math.ceil

@OptIn(ExperimentalMaterialApi::class)
@Destination<PlaceGraph>()
@Composable
fun TrangTimKiemNoiBan(
    navigator: DestinationsNavigator,
    ten: String,
) {
    val viewModel = hiltViewModel<TrangTimKiemNoiBanViewModel>()
    viewModel.ten = ten

    val state = viewModel.state

    val isRefreshing by remember {
        mutableStateOf(false)
    }

    val refreshState =
        rememberPullRefreshState(refreshing = isRefreshing, onRefresh = { viewModel.reset() })

    LaunchedEffect(true) {
        viewModel.loadNext()
    }

    if (viewModel.loading.value) {
        CircleProgressIndicator()
    } else {
        Box(
            Modifier
                .pullRefresh(refreshState),
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                items(items = state.ds) {
                    if (it == state.ds.last() && !state.isEnd && !state.isLoading) {
                        viewModel.loadNext()
                    }
                    Surface(
                        shadowElevation = 2.dp,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.padding(6.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(10.dp))
                                .background(MaterialTheme.colorScheme.primaryContainer)
                                .padding(5.dp)
                                .clickable {
                                    BaseViewModel.dsNavItem[0].backStack.add(
                                        TrangChiTietNoiBanDestination(it.id)
                                    )
                                    navigator.navigate(BaseViewModel.dsNavItem[0].backStack.last())
                                },
                        ) {
                            var checked by remember {
                                mutableStateOf(false)
                            }
                            val context = LocalContext.current
                            Text(
                                text = it.ten,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                maxLines = 1
                            )
                            Row(Modifier.padding(vertical = 8.dp)) {
                                for (index in 1..5) {
                                    if (ceil(it.diem_danh_gia) >= index) {
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = null,
                                            tint = Color.Yellow,
                                            modifier = Modifier.size(13.dp)
                                        )
                                    } else {
                                        Icon(
                                            imageVector = Icons.TwoTone.Star,
                                            contentDescription = null,
                                            modifier = Modifier.size(13.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.weight(1F))
                                IconToggleButton(
                                    checked = checked,
                                    onCheckedChange = { isChecked ->
                                        if (isChecked) {
                                            Toast.makeText(
                                                context,
                                                "Đã yêu thích ${it.ten}.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        } else {
                                            Toast.makeText(
                                                context,
                                                "Đã hủy yêu thích ${it.ten}.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        checked = isChecked
                                    }, modifier = Modifier.size(16.dp)
                                ) {
                                    if (checked) {
                                        Icon(
                                            imageVector = Icons.Default.Favorite,
                                            contentDescription = "Đã yêu thích",
                                            tint = Color(255, 105, 180)
                                        )
                                    } else {
                                        Icon(
                                            imageVector = Icons.Default.FavoriteBorder,
                                            contentDescription = "Chưa yêu thích",
                                        )
                                    }
                                }
                            }
                            Text(
                                text = it.mo_ta ?: "Chưa có thông tin",
                                fontSize = 12.sp,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                }
                item {
                    if (state.isLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircleProgressIndicator(
                                strokeWidth = 3.dp,
                                size = 35.dp,
                                innerPadding = PaddingValues(5.dp),
                                outerPadding = PaddingValues(10.dp),
                            )
                        }
                    }
                }
            }
            PullRefreshIndicator(
                isRefreshing,
                refreshState,
                Modifier.align(Alignment.TopCenter)
            )
        }
    }
}
