package com.example.appcsn.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.appcsn.data.model.NguoiDung
import com.example.appcsn.data.model.dacsan.DacSan
import com.example.appcsn.screen.destinations.TrangChiTietDacSanDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

open class BaseViewModel : ViewModel() {
    var loading = mutableStateOf(false)

    companion object {
        var nguoiDung by mutableStateOf<NguoiDung?>(null)
    }

    fun xemDacSan(
        navigator: DestinationsNavigator,
        dacSan: DacSan,
    ) {
        navigator.navigate(TrangChiTietDacSanDestination(dacSan = dacSan))
    }
}
