package com.example.appcsn.features.noiban.ui.view

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcsn.core.ui.navgraph.PlaceGraph
import com.example.appcsn.core.ui.viewmodel.BaseViewModel.Companion.dsNavItem
import com.example.appcsn.core.ui.widget.CircleProgressIndicator
import com.example.appcsn.core.ui.widget.PageHeader
import com.example.appcsn.core.ui.widget.StarBar
import com.example.appcsn.features.noiban.data.NoiBan
import com.example.appcsn.features.noiban.ui.viewmodel.TrangChuNoiBanViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.generated.destinations.TrangChiTietNoiBanDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<PlaceGraph>(start = true)
@Composable
fun TrangChuNoiBan(
    navigator: DestinationsNavigator,
    noiBanViewModel: TrangChuNoiBanViewModel
) {
    val context = LocalContext.current

    BackHandler {
        (context as Activity).finish()
    }

    if (noiBanViewModel.loading.value) {
        CircleProgressIndicator()
    } else {
        Column(
            Modifier
                .fillMaxSize()
        ) {
            PageHeader(text = "Nơi bán")
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                }
                items(items = noiBanViewModel.dsNoiBan)
                { noiBan ->
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .clickable {
                                dsNavItem[1].backStack.add(TrangChiTietNoiBanDestination(noiBan.id))
                                navigator.navigate(dsNavItem[1].backStack.last())
                            }
                    ) {
                        ThanhTieuDe(noiBan = noiBan)
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.primaryContainer)
                                .padding(10.dp)
                        ) {
                            Text(
                                text = "Mô tả: ${noiBan.mo_ta ?: "Chưa có thông tin"}",
                                fontSize = 13.sp,
                                maxLines = 2
                            )
                            Text(
                                text = "Địa chỉ: ${noiBan.dia_chi}",
                                fontSize = 13.sp,
                                maxLines = 2,
//                                modifier = Modifier
//                                    .clickable {
//                                        dsNavItem[1].backStack.add(TrangGoogleMapDestination)
//                                        navigator.navigate(dsNavItem[1].backStack.last())
//                                    }
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                StarBar(grade = noiBan.diem_danh_gia)
                                Text(
                                    text = " / ${noiBan.luot_danh_gia} lượt đánh giá",
                                    fontSize = 13.sp,
                                    maxLines = 1
                                )
                                Spacer(modifier = Modifier.weight(1F))
                            }
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}

@Composable
private fun ThanhTieuDe(
    noiBan: NoiBan
) {
    Text(
        text = noiBan.ten,
        fontSize = 15.sp,
        color = Color.White,
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                shape = RoundedCornerShape(
                    topStart = 10.dp,
                    topEnd = 10.dp
                )
            )
            .background(Color(30, 144, 255))
            .padding(vertical = 4.dp, horizontal = 10.dp)
    )
}