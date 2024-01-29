package com.example.appcsn.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
import com.example.appcsn.R
import com.example.appcsn.models.DacSan
import com.example.appcsn.models.DanhSachVungMien
import com.example.appcsn.models.MuaDacSan
import com.example.appcsn.models.NguyenLieu
import com.example.appcsn.models.VungMien
import com.example.appcsn.screens.destinations.TrangChiTietDacSanDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun TrangTimKiemDacSan(
    navigator: DestinationsNavigator,
    ten: String?,
    dsVungMien: DanhSachVungMien,
    dsMuaDacSan: List<MuaDacSan> = listOf(),
    dsNguyenLieu: List<NguyenLieu> = listOf(),
) {
    var dsDacSan = remember { mutableStateListOf<DacSan>() }
    if (ten != null) {
        val apiDacSan =
            HttpHelper.DacSanAPI.getInstance().create(DacSan.doc::class.java)

        LaunchedEffect(Unit) {
            val kq = apiDacSan.docTheoTen(ten)
            var temp = listOf<DacSan>()

            if (kq.body() != null) {
                temp = kq.body()!!
            }

            for (vungMien in dsVungMien.ds) {
                dsDacSan.addAll(temp.filter { e -> e.vung_mien.any { x -> x.id == vungMien.id } })
            }

            for (muaDacSan in dsMuaDacSan) {
                dsDacSan.addAll(temp.filter { e -> e.mua_dac_san.any { x -> x.id == muaDacSan.id } })
            }

            for (nguyenLieu in dsNguyenLieu) {
                dsDacSan.addAll(temp.filter { e -> e.thanh_phan.any { x -> x.nguyen_lieu.id == nguyenLieu.id } })
            }

            if (dsVungMien.ds.isEmpty() && dsMuaDacSan.isEmpty() && dsNguyenLieu.isEmpty())
            {
                dsDacSan.addAll(temp)
            }
        }
    } else {
        val apiDacSan =
            HttpHelper.DacSanAPI.getInstance().create(DacSan.doc::class.java)

        LaunchedEffect(Unit) {
            val kq = apiDacSan.doc()
            var temp = listOf<DacSan>()

            if (kq.body() != null) {
                temp = kq.body()!!
            }

            for (vungMien in dsVungMien.ds) {
                dsDacSan.addAll(temp.filter { e -> e.vung_mien.any { x -> x.id == vungMien.id } })
            }

            for (muaDacSan in dsMuaDacSan) {
                dsDacSan.addAll(temp.filter { e -> e.mua_dac_san.any { x -> x.id == muaDacSan.id } })
            }

            for (nguyenLieu in dsNguyenLieu) {
                dsDacSan.addAll(temp.filter { e -> e.thanh_phan.any { x -> x.nguyen_lieu.id == nguyenLieu.id } })
            }

            if (dsVungMien.ds.isEmpty() && dsMuaDacSan.isEmpty() && dsNguyenLieu.isEmpty())
            {
                dsDacSan.addAll(temp)
            }
        }
    }



    Column(
        Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            items(items = dsDacSan)
            {
                if (dsDacSan.indexOf(it) == 0) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                    )
                }
                Surface(
                    shadowElevation = 3.dp,
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.padding(10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(shape = RoundedCornerShape(15.dp))
                            .background(Color(65, 105, 225))
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
                            error = painterResource(id = R.drawable.image_not_found_128),
                            modifier = Modifier.size(100.dp)
                        )
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(
                                text = it.ten,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color.White,
                                maxLines = 1
                            )
                            Row() {
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
                            var moTa = "Chưa có thông tin"
                            if (it.mo_ta != null) {
                                moTa = it.mo_ta
                            }
                            Text(
                                text = "Mô tả: $moTa",
                                fontSize = 13.sp,
                                color = Color.White,
                                maxLines = 2
                            )
                        }
                    }
                }
            }
        }
    }
}