package com.example.appcsn.features.nguoidung.ui.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.appcsn.R
import com.example.appcsn.core.ui.back
import com.example.appcsn.core.ui.nav.SettingGraph
import com.example.appcsn.core.ui.viewmodel.BaseViewModel
import com.example.appcsn.core.ui.widget.CircleProgressIndicator
import com.example.appcsn.core.ui.widget.PageHeader
import com.example.appcsn.features.nguoidung.ui.viewmodel.TrangDanhSachYeuThichViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.generated.destinations.TrangChiTietDacSanDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlin.math.ceil

@OptIn(ExperimentalMaterialApi::class)
@Destination<SettingGraph>
@Composable
fun TrangDanhSachYeuThich(
    navigator: DestinationsNavigator,
    viewModel: TrangDanhSachYeuThichViewModel
) {
    BackHandler {
        back(navigator, 2)
    }
    val isRefreshing by remember {
        mutableStateOf(false)
    }
    val refreshState =
        rememberPullRefreshState(refreshing = isRefreshing, onRefresh = { viewModel.reset() })

    Column {
        PageHeader(text = "Danh sách đặc sản yêu thích")
        Box(
            modifier = Modifier.pullRefresh(refreshState)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                items(items = viewModel.state.itemList)
                {
                    if (it == viewModel.state.itemList.last() && !viewModel.state.isEnd && !viewModel.state.isLoading) {
                        viewModel.loadNext()
                    }
                    Surface(
                        shadowElevation = 2.dp,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.padding(6.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(10.dp))
                                .background(MaterialTheme.colorScheme.primaryContainer)
                                .clickable {
                                    BaseViewModel.dsNavItem[0].backStack.add(
                                        TrangChiTietDacSanDestination(
                                            it.id,
                                            0
                                        )
                                    )
                                    navigator.navigate(BaseViewModel.dsNavItem[0].backStack.last())
                                },
                        ) {
                            AsyncImage(
                                contentScale = ContentScale.Crop,
                                model = it.hinh_dai_dien.url,
                                contentDescription = it.ten,
                                error = painterResource(id = R.drawable.image_not_found_128),
                                modifier = Modifier.size(115.dp)
                            )
                            Column(
                                modifier = Modifier.padding(
                                    horizontal = 8.dp,
                                    vertical = 4.dp
                                )
                            ) {
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
                }
                item {
                    if (viewModel.state.isLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircleProgressIndicator(
                                strokeWidth = 4.dp,
                                size = 35.dp,
                                backgroundColor = MaterialTheme.colorScheme.surface
                            )
                        }
                    }
                }
            }
        }
    }
}