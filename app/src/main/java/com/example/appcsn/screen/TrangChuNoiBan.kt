package com.example.appcsn.screen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcsn.ui.CircleProgressIndicator
import com.example.appcsn.ui.PageHeader
import com.example.appcsn.ui.navgraph.PlaceGraph
import com.example.appcsn.viewmodel.TrangChuNoiBanViewModel
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
                items(items = noiBanViewModel.dsNoiBan)
                {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color(10, 124, 235),
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable {
                                navigator.navigate(TrangChiTietNoiBanDestination(it))
                            }
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        ) {
                            Text(
                                text = it.ten,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Color.White,
                                maxLines = 1
                            )
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
