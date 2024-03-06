package com.example.appcsn.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.appcsn.data.model.NguoiDung
import com.example.appcsn.data.model.dacsan.DacSan
import com.example.appcsn.screen.destinations.TrangChiTietDacSanDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

open class BaseViewModel : ViewModel() {
    var loading = mutableStateOf(false)

    companion object {
        var nguoiDung by mutableStateOf<NguoiDung?>(null)

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

    fun xemDacSan(
        navigator: DestinationsNavigator,
        dacSan: DacSan,
    ) {
        navigator.navigate(TrangChiTietDacSanDestination(dacSan = dacSan))
    }
}
