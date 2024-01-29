package com.example.appcsn.screens

import android.util.Log
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
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.appcsn.HttpHelper
import com.example.appcsn.models.DacSan
import com.example.appcsn.models.DanhSachVungMien
import com.example.appcsn.models.VungMien
import com.example.appcsn.screens.destinations.TrangChiTietDacSanDestination
import com.example.appcsn.screens.destinations.TrangTimKiemDacSanDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlin.math.log

@RootNavGraph(start = true)
@Destination
@Composable
fun TrangChuDacSan(
//    navController: NavController,
    navigator: DestinationsNavigator,
    dsDacSan: List<DacSan>
) {
    val dsVungMien = remember {
        mutableStateListOf<VungMien>()
    }

    LaunchedEffect(key1 = true) {
        val vungMienAPI =
            HttpHelper.DacSanAPI.getInstance().create(VungMien.docAPI::class.java)

        val kq = vungMienAPI.docDanhSach()

        if (kq.body() != null) {
            dsVungMien.addAll(kq.body()!!)
        }
    }

    Column(
        Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )
        LazyColumn() {
            items(dsVungMien) {vungMien ->
                val dsDacSanTheoVung = dsDacSan.filter { it.vung_mien.any { temp -> temp.id == vungMien.id } }
                if (dsDacSanTheoVung.isNotEmpty()) {
                    Text(
                        text = "Đặc sản ${vungMien.ten}",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(30, 144, 255))
                            .padding(5.dp)
                            .clickable {
                                navigator.navigate(TrangTimKiemDacSanDestination(
                                    ten = null,
                                    dsVungMien = DanhSachVungMien(listOf(vungMien))
                                ))
                            }
                    )
                    LazyRow(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        items(dsDacSanTheoVung)
                        {
                            Surface(
                                shadowElevation = 3.dp,
                                shape = RoundedCornerShape(15.dp),
                                modifier = Modifier.padding(10.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .width(100.dp)
                                        .height(125.dp)
                                        .clip(shape = RoundedCornerShape(10.dp))
                                        .background(Color(50, 100, 200))
                                        .clickable {
//                            navController.navigate("${Screen.TrangDacSan.TrangChiTietDacSan.route}/${it.id}")
                                            navigator.navigate(TrangChiTietDacSanDestination(dacSan = it))
                                        },
                                ) {
                                    var checked by remember {
                                        mutableStateOf(false)
                                    }
                                    val context = LocalContext.current
                                    AsyncImage(
                                        contentScale = ContentScale.Crop,
                                        model = it.hinh_dai_dien.url,
                                        contentDescription = it.ten,
                                        error = painterResource(id = com.example.appcsn.R.drawable.image_not_found_128),
                                        modifier = Modifier
                                            .width(100.dp)
                                            .height(75.dp)
                                    )
                                    Text(
                                        text = it.ten,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = Color.White,
                                        maxLines = 1,
                                        modifier = Modifier.padding(5.dp)
                                    )
                                    Row(modifier = Modifier.padding(horizontal = 5.dp)) {
                                        Text(
                                            text = it.diem_danh_gia.toString(),
                                            fontSize = 12.sp,
                                            color = Color.White,
                                            maxLines = 1
                                        )
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
                                                    tint = Color.White
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