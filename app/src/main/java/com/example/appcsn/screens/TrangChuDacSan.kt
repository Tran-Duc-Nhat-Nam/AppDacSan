package com.example.appcsn.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.appcsn.models.DacSan
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph(start = true)
@Destination
@Composable
fun TrangChuDacSan(
    navController: NavController,
    dsDacSan: List<DacSan>
) {
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
                    Box(modifier = Modifier.height(100.dp))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clip(shape = RoundedCornerShape(10))
                        .background(Color.DarkGray)
                        .clickable {
                            navController.navigate("${Screen.TrangDacSan.TrangChiTietDacSan.route}/${it.id}")
                        },
                ) {
                    AsyncImage(
                        contentScale = ContentScale.Crop,
                        model = it.hinh_dai_dien.url,
                        contentDescription = it.ten,
                        error = painterResource(id = com.example.appcsn.R.drawable.image_not_found_128),
                        modifier = Modifier.size(100.dp)
                    )
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = it.ten,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.White,
                            maxLines = 1
                        )
                        var moTa = "Chưa có thông tin"
                        if (it.mo_ta != null) {
                            moTa = it.mo_ta
                        }
                        Text(
                            text = "Mô tả: $moTa",
                            fontSize = 16.sp,
                            color = Color.White,
                            maxLines = 2
                        )
                    }
                }
            }
        }
    }
}