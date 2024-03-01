package com.example.appcsn.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.appcsn.data.model.dacsan.LuotDanhGiaDacSan
import com.example.appcsn.data.repository.DacSanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrangChiTietDacSanViewModel @Inject constructor(
    private val dacSanRepository: DacSanRepository
) : BaseViewModel() {
    var luotDanhGiaDacSan by mutableStateOf(LuotDanhGiaDacSan())
    var diemDanhGia by mutableIntStateOf(0)
    var noiDung by mutableStateOf("")
}
