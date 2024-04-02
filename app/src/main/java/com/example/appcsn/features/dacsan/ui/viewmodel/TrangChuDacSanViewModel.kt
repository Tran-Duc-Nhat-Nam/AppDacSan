package com.example.appcsn.features.dacsan.ui.viewmodel

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.viewModelScope
import com.example.appcsn.core.ui.viewmodel.BaseViewModel
import com.example.appcsn.features.dacsan.data.DacSan
import com.example.appcsn.features.dacsan.data.TuKhoaTimKiem
import com.example.appcsn.features.dacsan.data.VungMien
import com.example.appcsn.features.dacsan.domain.repository.DacSanRepository
import com.example.appcsn.features.dacsan.domain.repository.VungMienRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrangChuDacSanViewModel @Inject constructor(
    private val dacSanRepository: DacSanRepository,
    private val vungMienRepository: VungMienRepository
) : BaseViewModel() {
    var dsDacSanVungMien: MutableMap<VungMien, List<DacSan>> = mutableStateMapOf()

    init {
        viewModelScope.launch {
            loading.value = true
            val job = viewModelScope.launch {
                docVungMien()
            }
            job.join()
            val job2 = viewModelScope.launch {
                docDuLieu()
            }
            job2.join()
            loading.value = false
        }
    }

    private suspend fun docDuLieu() {
        for (vm in dsDacSanVungMien.keys) {
            val kq =
                dacSanRepository.timKiem("", TuKhoaTimKiem(dsVungMien = mutableListOf(vm.id)), 5, 0)

            if (kq.getOrNull() != null) {
                dsDacSanVungMien[vm] = kq.getOrNull()!!
            }
        }
    }

    private suspend fun docVungMien() {
        val kq = vungMienRepository.doc()

        if (kq.getOrNull() != null) {
            val dsVM = kq.getOrNull()!!
            for (vm in dsVM) {
                dsDacSanVungMien[vm] = emptyList()
            }
        }
    }
}
