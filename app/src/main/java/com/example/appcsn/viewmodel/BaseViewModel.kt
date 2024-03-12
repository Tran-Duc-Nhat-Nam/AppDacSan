package com.example.appcsn.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.appcsn.data.model.NguoiDung
import com.example.appcsn.ui.navgraph.NavItem
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.generated.destinations.TrangChuDacSanDestination
import com.ramcosta.composedestinations.generated.destinations.TrangChuNoiBanDestination
import com.ramcosta.composedestinations.generated.destinations.TrangNguoiDungDestination
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

open class BaseViewModel : ViewModel() {
    var loading = mutableStateOf(false)

    companion object {
        var nguoiDung by mutableStateOf<NguoiDung?>(null)

        val dsNavItem = listOf(
            NavItem(
                graph = NavGraphs.food,
                backStack = mutableListOf(TrangChuDacSanDestination),
                index = 1,
                name = "Đặc sản",
                icon = Icons.Outlined.Home,
                selectedIcon = Icons.Default.Home
            ),
            NavItem(
                graph = NavGraphs.place,
                backStack = mutableListOf(TrangChuNoiBanDestination),
                index = 2,
                name = "Nơi bán",
                icon = Icons.Outlined.LocationOn,
                selectedIcon = Icons.Default.LocationOn
            ),
            NavItem(
                graph = NavGraphs.root,
                backStack = mutableListOf(TrangNguoiDungDestination),
                index = 3,
                name = "Người dùng",
                icon = Icons.Outlined.Person,
                selectedIcon = Icons.Default.Person
            )
        )

        fun toLocalDate(date: Date): LocalDate {
            return date.toInstant()
                .atZone(
                    ZoneId.systemDefault()
                ).toLocalDate()
        }

        fun toLocalDate(time: Long): LocalDate {
            return Instant.ofEpochMilli(time)
                .atZone(
                    ZoneId.systemDefault()
                ).toLocalDate()
        }
    }
}
