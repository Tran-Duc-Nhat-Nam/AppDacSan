package com.example.appcsn.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
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
        val kq = dacSanRepository.docDanhSach()

        if (kq.getOrNull() != null) {
            dsDacSan.addAll(kq.getOrNull()!!)
            Log.d("Data", "Đặc sản: ${dsDacSan.size}")
        }
    }

    private suspend fun docVungMien() {
        val kq = vungMienRepository.docDanhSach()

        if (kq.getOrNull() != null) {
            dsVungMien.addAll(kq.getOrNull()!!)
            Log.d("Data", "Vùng miền: ${dsVungMien.size}")
        }
    }

    suspend fun like(id: Int): Boolean {
        val kq: Result<Boolean>

        try {
            kq = dacSanRepository.like(id, nguoiDung!!.id)
        } catch (_: Exception) {
            return false
        }

        return kq.getOrNull() ?: false
    }

    suspend fun unlike(id: Int): Boolean {
        val kq: Result<Boolean>

        try {
            kq = dacSanRepository.unlike(id, nguoiDung!!.id)
        } catch (_: Exception) {
            return false
        }

        return kq.getOrNull() ?: false
    }

    suspend fun checkLike(id: Int): Boolean {
        val kq: Result<Boolean>

        try {
            kq = dacSanRepository.checkLike(id, nguoiDung!!.id)
        } catch (_: Exception) {
            return false
        }

        return kq.getOrNull() ?: false
    }
}
