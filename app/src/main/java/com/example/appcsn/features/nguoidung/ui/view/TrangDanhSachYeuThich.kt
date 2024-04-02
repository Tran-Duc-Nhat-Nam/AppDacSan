package com.example.appcsn.features.nguoidung.ui.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.appcsn.core.ui.navgraph.SettingGraph
import com.example.appcsn.core.ui.viewmodel.BaseViewModel
import com.example.appcsn.core.ui.widget.CircleProgressIndicator
import com.example.appcsn.core.ui.widget.PageHeader
import com.example.appcsn.features.nguoidung.ui.viewmodel.TrangDanhSachYeuThichViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.generated.destinations.TrangChiTietDacSanDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlin.math.ceil

@Destination<SettingGraph>
@Composable
fun TrangDanhSachYeuThich(
    navigator: DestinationsNavigator,
    viewModel: TrangDanhSachYeuThichViewModel
) {
    BackHandler {
        BaseViewModel.dsNavItem[2].backStack.removeLast()
        navigator.navigate(BaseViewModel.dsNavItem[2].backStack.last())
    }

    if (viewModel.loading.value) {
        CircleProgressIndicator()
    } else {
        Column {
            PageHeader(text = "Danh sách đặc sản yêu thích")
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                items(items = viewModel.dsDacSan)
                {
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
            }
        }
    }
}