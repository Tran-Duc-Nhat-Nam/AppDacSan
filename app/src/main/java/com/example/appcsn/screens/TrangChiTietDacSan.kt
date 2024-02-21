package com.example.appcsn.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ChipColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.appcsn.HttpHelper
import com.example.appcsn.R
import com.example.appcsn.models.DacSan
import com.example.appcsn.models.DanhSachVungMien
import com.example.appcsn.models.NguoiDung
import com.example.appcsn.screens.destinations.TrangTimKiemDacSanDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalLayoutApi::class)
@Destination
@Composable
fun TrangChiTietDacSan(
    navigator: DestinationsNavigator,
    dacSan: DacSan,
    nguoiDung: NguoiDung?,
) {
    var nlOpen = remember {
        mutableStateOf(true)
    }
    LaunchedEffect(key1 = true) {
        if (nguoiDung != null) {
            val apiDacSan =
                HttpHelper.DacSanAPI.getInstance().create(DacSan.doc::class.java)

            val kq = apiDacSan.xem(dacSan.id, nguoiDung.id)
        }
    }
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        item {
            AsyncImage(
                contentScale = ContentScale.Crop,
                model = dacSan.hinh_dai_dien.url,
                contentDescription = dacSan.ten,
                error = painterResource(id = R.drawable.image_not_found_512),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(corner = CornerSize(10.dp)))
            )
            Text(
                text = dacSan.ten,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                maxLines = 1,
                modifier = Modifier
                    .padding(5.dp)
            )
            var moTa = "Chưa có thông tin"
            if (dacSan.mo_ta != null) {
                moTa = dacSan.mo_ta
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Mô tả",
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = moTa,
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(corner = CornerSize(10.dp)))
                    .background(Color.DarkGray)
                    .padding(10.dp)
            )
            var cachCheBien = "Chưa có thông tin"
            if (dacSan.cach_che_bien != null) {
                cachCheBien = dacSan.cach_che_bien
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Cách chế biến",
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = cachCheBien,
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(corner = CornerSize(10.dp)))
                    .background(Color.DarkGray)
                    .padding(10.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Vùng miền",
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(10.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(7.dp),
                verticalArrangement = Arrangement.spacedBy(7.dp),
                modifier = Modifier
                    .padding(7.dp)
            ) {
                dacSan.vung_mien.forEach { vungMien ->
                    AssistChip(
                        colors = ChipColors(
                            containerColor = Color.Blue,
                            labelColor = Color.White,
                            leadingIconContentColor = Color.Cyan,
                            trailingIconContentColor = Color.Magenta,
                            disabledContainerColor = Color.Gray,
                            disabledLabelColor = Color.Gray,
                            disabledLeadingIconContentColor = Color.Gray,
                            disabledTrailingIconContentColor = Color.Gray,
                        ),
                        onClick = {
                            navigator.navigate(
                                TrangTimKiemDacSanDestination(
                                    ten = null,
                                    dsVungMien = DanhSachVungMien(listOf(vungMien))
                                )
                            )
                        },
                        label = { Text(text = vungMien.ten) })
                }
            }
        }
    }
}
