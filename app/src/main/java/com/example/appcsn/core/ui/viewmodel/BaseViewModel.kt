package com.example.appcsn.core.ui.viewmodel

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import com.example.appcsn.core.ui.nav.NavItem
import com.example.appcsn.features.nguoidung.data.NguoiDung
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.generated.destinations.TrangCaiDatDestination
import com.ramcosta.composedestinations.generated.destinations.TrangChuDacSanDestination
import com.ramcosta.composedestinations.generated.destinations.TrangChuNoiBanDestination
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

open class BaseViewModel : ViewModel() {
    var loading = mutableStateOf(false)

    companion object {
        var nguoiDung by mutableStateOf<NguoiDung?>(null)

        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settingPrefs")

        var currentGraph = 0

        val dsNavItem = listOf(
            NavItem(
                graph = NavGraphs.food,
                backStack = mutableStateListOf(TrangChuDacSanDestination),
                index = 1,
                name = "Đặc sản",
                icon = Icons.Outlined.Home,
                selectedIcon = Icons.Default.Home
            ),
            NavItem(
                graph = NavGraphs.place,
                backStack = mutableStateListOf(TrangChuNoiBanDestination),
                index = 2,
                name = "Nơi bán",
                icon = Icons.Outlined.LocationOn,
                selectedIcon = Icons.Default.LocationOn
            ),
            NavItem(
                graph = NavGraphs.setting,
                backStack = mutableStateListOf(TrangCaiDatDestination),
                index = 3,
                name = "Cài đặt",
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

        fun kiemTraNguoiDung(
            nguoiDung: NguoiDung?,
            yeuCauXacMinh: Boolean = true,
        ): Boolean {
            return if (Firebase.auth.currentUser != null && nguoiDung != null) {
                if (yeuCauXacMinh) Firebase.auth.currentUser!!.isEmailVerified
                else true
            } else false
        }
    }
}
