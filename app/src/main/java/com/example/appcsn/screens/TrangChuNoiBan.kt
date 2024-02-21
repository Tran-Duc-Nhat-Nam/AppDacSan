package com.example.appcsn.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcsn.models.NoiBan
import com.example.appcsn.screens.destinations.TrangChiTietNoiBanDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun TrangChuNoiBan(
//    navController: NavController,
    navigator: DestinationsNavigator,
    dsNoiBan: List<NoiBan>
) {
    Column(
        Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            items(items = dsNoiBan)
            {
                Button(
                    onClick = {
//                        navController.navigate("${Screen.TrangNoiBan.TrangChiTietNoiBan.route}/${it.id}")
                        navigator.navigate(TrangChiTietNoiBanDestination(noiBan = it))
                    },
                    colors = ButtonDefaults.buttonColors(Color.DarkGray),
                    modifier = Modifier
                        .padding(10.dp)
                        .clip(shape = RoundedCornerShape(10))
                        .background(Color.DarkGray)
                        .padding(10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
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
