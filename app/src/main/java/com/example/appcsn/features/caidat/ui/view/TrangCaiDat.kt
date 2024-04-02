package com.example.appcsn.features.caidat.ui.view

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcsn.core.ui.navgraph.SettingGraph
import com.example.appcsn.core.ui.viewmodel.BaseViewModel.Companion.dsNavItem
import com.example.appcsn.core.ui.widget.PageHeader
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.generated.destinations.TrangCaiDatGiaoDienDestination
import com.ramcosta.composedestinations.generated.destinations.TrangDanhSachYeuThichDestination
import com.ramcosta.composedestinations.generated.destinations.TrangLichSuXemDestination
import com.ramcosta.composedestinations.generated.destinations.TrangNguoiDungDestination
import com.ramcosta.composedestinations.generated.destinations.TrangXemWebDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<SettingGraph>(start = true)
@Composable
fun TrangCaiDat(
    navigator: DestinationsNavigator,
) {
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current

    BackHandler {
        (context as Activity).finish()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        PageHeader(text = "Cài đặt")
        Text(
            text = "Tài khoản",
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .clickable {
                    dsNavItem[2].backStack.add(TrangNguoiDungDestination)
                    navigator.navigate(dsNavItem[2].backStack.last())
                }
        )
        HorizontalDivider()
        Text(
            text = "Danh sách yêu thích đặc sản",
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .clickable {
                    dsNavItem[2].backStack.add(TrangDanhSachYeuThichDestination)
                    navigator.navigate(dsNavItem[2].backStack.last())
                }
        )
        HorizontalDivider()
        Text(
            text = "Lịch sử xem đặc sản",
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .clickable {
                    dsNavItem[2].backStack.add(TrangLichSuXemDestination)
                    navigator.navigate(dsNavItem[2].backStack.last())
                }
        )
        HorizontalDivider()
        Text(
            text = "Giao diện",
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .clickable {
                    dsNavItem[2].backStack.add(TrangCaiDatGiaoDienDestination)
                    navigator.navigate(dsNavItem[2].backStack.last())
                }
        )
        HorizontalDivider()
        Text(
            text = "App đặc sản VinaFood - 2024",
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .clickable {
                    dsNavItem[2].backStack.add(TrangXemWebDestination)
                    navigator.navigate(dsNavItem[2].backStack.last())
                }
        )
    }
}