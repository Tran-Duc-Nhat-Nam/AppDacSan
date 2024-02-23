package com.example.appcsn.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.appcsn.R
import com.example.appcsn.data.model.DanhSachMuaDacSan
import com.example.appcsn.data.model.DanhSachNguyenLieu
import com.example.appcsn.data.model.DanhSachVungMien
import com.example.appcsn.screen.destinations.TrangTimKiemDacSanDestination
import com.example.appcsn.viewmodel.BaseViewModel
import com.example.appcsn.viewmodel.TrangChuDacSanViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@RootNavGraph(start = true)
@Destination
@Composable
fun TrangChuDacSan(
    navigator: DestinationsNavigator,
    dacSanViewModel: TrangChuDacSanViewModel
) {
    val coroutineScope = rememberCoroutineScope()

    if (dacSanViewModel.loading.value) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                strokeWidth = 6.dp,
                modifier = Modifier.size(85.dp)
            )
        }
    } else {
        Box(modifier = Modifier.padding(horizontal = 10.dp)) {
            Column {
                Surface(shape = RoundedCornerShape(5.dp), shadowElevation = 3.dp) {
                    Text(
                        color = Color.White,
                        text = "Đặc sản Việt Nam",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(5.dp))
                            .background(Color(10, 124, 235))
                            .padding(vertical = 10.dp)
                    )
                }
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
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
                                    .padding(10.dp)
                                    .clip(
                                        shape = RoundedCornerShape(10.dp)
                                    )

                            ) {
                                Text(
                                    text = "Đặc sản ${vungMien.ten}",
                                    fontWeight = FontWeight.W500,
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
                                        .padding(vertical = 5.dp, horizontal = 10.dp)
                                        .clickable {
                                            navigator.navigate(
                                                TrangTimKiemDacSanDestination(
                                                    ten = null,
                                                    dsVungMien = DanhSachVungMien(listOf(vungMien)),
                                                    dsMuaDacSan = DanhSachMuaDacSan(),
                                                    dsNguyenLieu = DanhSachNguyenLieu(),
                                                )
                                            )
                                        }
                                )
                                LazyRow(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(MaterialTheme.colorScheme.primaryContainer)
                                        .padding(5.dp),
                                ) {
                                    items(dsDacSanTheoVung)
                                    {
                                        Row {
                                            HorizontalDivider(
                                                thickness = 10.dp,
                                                color = Color.Black
                                            )
                                            Column(
                                                modifier = Modifier
                                                    .width(100.dp)
                                                    .height(150.dp)
                                                    .padding(horizontal = 5.dp)
                                                    .clickable {
                                                        dacSanViewModel.xemDacSan(navigator, it)
                                                    },
                                            ) {
                                                var checked by remember { mutableStateOf(false) }
                                                val context = LocalContext.current
                                                if (BaseViewModel.nguoiDung != null) {
                                                    LaunchedEffect(key1 = true) {
                                                        checked = dacSanViewModel.checkLike(it.id)
                                                    }
                                                }
                                                AsyncImage(
                                                    contentScale = ContentScale.Crop,
                                                    model = it.hinh_dai_dien.url,
                                                    contentDescription = it.ten,
                                                    error = painterResource(id = R.drawable.image_not_found_128),
                                                    modifier = Modifier
                                                        .size(100.dp)
                                                        .padding(top = 6.dp)
                                                        .clip(shape = RoundedCornerShape(5.dp))
                                                )
                                                Text(
                                                    text = it.ten,
                                                    fontWeight = FontWeight.W500,
                                                    fontSize = 14.sp,
                                                    maxLines = 1,
                                                )
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                ) {
                                                    Text(
                                                        text = it.diem_danh_gia.toString(),
                                                        fontSize = 12.sp,
                                                        maxLines = 1
                                                    )
                                                    Spacer(modifier = Modifier.weight(1F))
                                                    IconToggleButton(
                                                        checked = checked,
                                                        onCheckedChange = { isChecked ->
                                                            if (BaseViewModel.nguoiDung != null) {
                                                                if (isChecked) {
                                                                    coroutineScope.launch {
                                                                        val kq =
                                                                            dacSanViewModel.like(it.id)
                                                                        if (kq) {
                                                                            Toast.makeText(
                                                                                context,
                                                                                "Đã yêu thích ${it.ten}.",
                                                                                Toast.LENGTH_SHORT
                                                                            ).show()
                                                                        }
                                                                    }
                                                                } else {
                                                                    Toast.makeText(
                                                                        context,
                                                                        "Đã hủy yêu thích ${it.ten}.",
                                                                        Toast.LENGTH_SHORT
                                                                    ).show()
                                                                }
                                                                checked = isChecked
                                                            } else {
                                                                Toast.makeText(
                                                                    context,
                                                                    "Vui lòng đăng nhập để sử dụng tính năng này.",
                                                                    Toast.LENGTH_SHORT
                                                                ).show()
                                                            }
                                                        }, modifier = Modifier.size(14.dp)
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
