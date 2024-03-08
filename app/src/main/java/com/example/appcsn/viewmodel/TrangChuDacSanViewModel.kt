package com.example.appcsn.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.viewModelScope
import com.example.appcsn.data.model.VungMien
import com.example.appcsn.data.model.dacsan.DacSan
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
    var dsDacSan = mutableStateListOf<DacSan>()
    var dsVungMien = mutableStateListOf<VungMien>()
    val dsYeuThichDacSan = mutableStateMapOf<Int, Boolean>()

    init {
        viewModelScope.launch {
            loading.value = true
            val job = viewModelScope.launch {
                docVungMien()
                docDuLieu()
            }
            job.join()
            loading.value = false
        }
    }

    private suspend fun docDuLieu() {
        val kq = dacSanRepository.doc()

        if (kq.getOrNull() != null) {
            dsDacSan.addAll(kq.getOrNull()!!)
            Log.d("Data", "Đặc sản: ${dsDacSan.size}")
            for (dacSan in dsDacSan) {
                checkLike(dacSan.id)
            }
        }
    }

    private suspend fun docVungMien() {
        val kq = vungMienRepository.doc()

        if (kq.getOrNull() != null) {
            dsVungMien.addAll(kq.getOrNull()!!)
            Log.d("Data", "Vùng miền: ${dsVungMien.size}")
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
