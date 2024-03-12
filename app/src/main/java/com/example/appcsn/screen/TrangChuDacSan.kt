package com.example.appcsn.screen

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.appcsn.R
import com.example.appcsn.data.model.dacsan.TuKhoaTimKiem
import com.example.appcsn.ui.navgraph.FoodGraph
import com.example.appcsn.ui.widget.CircleProgressIndicator
import com.example.appcsn.ui.widget.PageHeader
import com.example.appcsn.ui.widget.StarBar
import com.example.appcsn.viewmodel.BaseViewModel
import com.example.appcsn.viewmodel.BaseViewModel.Companion.dsNavItem
import com.example.appcsn.viewmodel.TrangChuDacSanViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.generated.destinations.TrangChiTietDacSanDestination
import com.ramcosta.composedestinations.generated.destinations.TrangTimKiemDacSanDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@Destination<FoodGraph>(start = true)
@Composable
fun TrangChuDacSan(
    navigator: DestinationsNavigator,
    dacSanViewModel: TrangChuDacSanViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    BackHandler {
        (context as Activity).finish()
    }

    if (dacSanViewModel.loading.value) {
        CircleProgressIndicator()
    } else {
        Box {
            Column {
                PageHeader(text = "Đặc sản")
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 6.dp)
                ) {
                    items(dacSanViewModel.dsVungMien) { vungMien ->
                        val dsDacSanTheoVung =
                            dacSanViewModel.dsDacSan.filter { it.vung_mien.any { temp -> temp.id == vungMien.id } }
                        if (dacSanViewModel.dsVungMien.indexOf(vungMien) == 0) {
                            Box(modifier = Modifier.height(10.dp))
                        }
                        if (dsDacSanTheoVung.isNotEmpty()) {
                            Column(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .clip(
                                        shape = RoundedCornerShape(10.dp)
                                    )

                            ) {
                                Text(
                                    text = "Đặc sản ${vungMien.ten}",
                                    fontSize = 14.sp,
                                    color = Color.White,
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
                                        .clickable {
                                            navigator.navigate(
                                                TrangTimKiemDacSanDestination(
                                                    ten = "",
                                                    tuKhoa = TuKhoaTimKiem(
                                                        dsVungMien = mutableListOf(
                                                            vungMien.id
                                                        )
                                                    )
                                                )
                                            )
                                        }
                                )
                                LazyRow(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(MaterialTheme.colorScheme.primaryContainer)
                                        .padding(vertical = 4.dp, horizontal = 4.dp),
                                ) {
                                    items(dsDacSanTheoVung)
                                    { dacSan ->
                                        Row {
                                            Column(
                                                modifier = Modifier
                                                    .padding(5.dp)
                                                    .width(115.dp)
                                                    .clickable {
                                                        dsNavItem[0].backStack.add(
                                                            TrangChiTietDacSanDestination(dacSan)
                                                        )
                                                        navigator.navigate(
                                                            dsNavItem[0].backStack.last(),
                                                            onlyIfResumed = true
                                                        )
                                                    },
                                            ) {
                                                AsyncImage(
                                                    contentScale = ContentScale.Crop,
                                                    model = dacSan.hinh_dai_dien.url,
                                                    contentDescription = dacSan.ten,
                                                    error = painterResource(id = R.drawable.image_not_found_128),
                                                    filterQuality = FilterQuality.None,
                                                    modifier = Modifier
                                                        .size(115.dp)
                                                        .clip(shape = RoundedCornerShape(8.dp))
                                                )
                                                Text(
                                                    text = dacSan.ten,
                                                    fontSize = 13.sp,
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis,
                                                    modifier = Modifier.padding(vertical = 4.dp)
                                                )
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    modifier = Modifier
                                                        .width(115.dp)
                                                ) {
                                                    Row {
                                                        StarBar(grade = dacSan.diem_danh_gia)
                                                    }
                                                    if (BaseViewModel.nguoiDung != null) {
                                                        IconToggleButton(
                                                            checked = dacSanViewModel.dsYeuThichDacSan[dacSan.id]!!,
                                                            onCheckedChange = { isChecked ->
                                                                coroutineScope.launch {
                                                                    if (isChecked) {
                                                                        val kq =
                                                                            dacSanViewModel.like(
                                                                                dacSan.id
                                                                            )
                                                                        if (kq) {
                                                                            Toast.makeText(
                                                                                context,
                                                                                "Đã yêu thích ${dacSan.ten}.",
                                                                                Toast.LENGTH_SHORT
                                                                            ).show()
                                                                        }
                                                                    } else {
                                                                        val kq =
                                                                            dacSanViewModel.unlike(
                                                                                dacSan.id
                                                                            )
                                                                        if (kq) {
                                                                            Toast.makeText(
                                                                                context,
                                                                                "Đã hủy yêu thích ${dacSan.ten}.",
                                                                                Toast.LENGTH_SHORT
                                                                            ).show()
                                                                        }
                                                                    }
                                                                }
                                                            }, modifier = Modifier.size(20.dp)
                                                        ) {
                                                            if (dacSanViewModel.dsYeuThichDacSan[dacSan.id]!!) {
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
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
