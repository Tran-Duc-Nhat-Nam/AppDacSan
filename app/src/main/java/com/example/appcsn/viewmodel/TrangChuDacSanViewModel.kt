package com.example.appcsn.viewmodel

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.viewModelScope
import com.example.appcsn.data.model.VungMien
import com.example.appcsn.data.model.dacsan.DacSan
import com.example.appcsn.data.model.dacsan.TuKhoaTimKiem
import com.example.appcsn.data.repository.DacSanRepository
import com.example.appcsn.data.repository.VungMienRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrangChuDacSanViewModel @Inject constructor(
    private val dacSanRepository: DacSanRepository,
    private val vungMienRepository: VungMienRepository
) : BaseViewModel() {
    var dsDacSanVungMien: MutableMap<VungMien, List<DacSan>> = mutableStateMapOf()
    val dsYeuThichDacSan = mutableStateMapOf<Int, Boolean>()

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
                for (dacSan in dsDacSanVungMien[vm]!!) {
                    checkLike(dacSan.id)
                }
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

    suspend fun like(id: Int): Boolean {
        val kq: Result<Boolean>

        try {
            kq = dacSanRepository.like(id, nguoiDung!!.id)
            dsYeuThichDacSan[id] = kq.getOrNull() == true
        } catch (_: Exception) {
            dsYeuThichDacSan[id] = false
        }

        return dsYeuThichDacSan[id]!!
    }

    suspend fun unlike(id: Int): Boolean {
        val kq: Result<Boolean>

        try {
            kq = dacSanRepository.unlike(id, nguoiDung!!.id)
            dsYeuThichDacSan[id] = kq.getOrNull() == false
        } catch (_: Exception) {
            dsYeuThichDacSan[id] = true
        }

        return dsYeuThichDacSan[id]!!
    }

    private suspend fun checkLike(id: Int): Boolean {
        return if (dsYeuThichDacSan[id] != null) {
            dsYeuThichDacSan[id]!!
        } else {
            val kq: Result<Boolean>

            try {
                kq = dacSanRepository.checkLike(id, nguoiDung!!.id)
                dsYeuThichDacSan[id] = kq.getOrNull() == true
            } catch (_: Exception) {
                dsYeuThichDacSan[id] = false
            }

            dsYeuThichDacSan[id]!!
        }
    }
}
