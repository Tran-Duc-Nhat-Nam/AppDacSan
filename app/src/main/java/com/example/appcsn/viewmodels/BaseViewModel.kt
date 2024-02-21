package com.example.appcsn.viewmodels

import androidx.lifecycle.ViewModel
import com.example.appcsn.models.DacSan
import com.example.appcsn.models.NguoiDung
import com.example.appcsn.screens.destinations.TrangChiTietDacSanDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

open class BaseViewModel : ViewModel() {
    companion object {
        var nguoiDung: NguoiDung? = null
        fun signIn(nd: NguoiDung) {
            nguoiDung = nd
        }
    }

    fun xemDacSan(
        navigator: DestinationsNavigator,
        dacSan: DacSan,
    ) {
        navigator.navigate(TrangChiTietDacSanDestination(dacSan = dacSan, nguoiDung = nguoiDung))
    }
}
