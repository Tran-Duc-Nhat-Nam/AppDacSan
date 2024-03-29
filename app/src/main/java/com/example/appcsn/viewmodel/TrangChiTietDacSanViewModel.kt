package com.example.appcsn.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.appcsn.data.model.HinhAnh
import com.example.appcsn.data.model.dacsan.DacSan
import com.example.appcsn.data.model.dacsan.LuotDanhGiaDacSan
import com.example.appcsn.data.repository.DacSanRepository
import com.example.appcsn.data.repository.HinhAnhRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrangChiTietDacSanViewModel @Inject constructor(
    private val dacSanRepository: DacSanRepository,
    private val hinhAnhRepository: HinhAnhRepository
) : BaseViewModel() {
    var dacSan = mutableStateOf<DacSan?>(null)
    var dsHinhAnh = mutableStateListOf<HinhAnh>()
    var dsDanhGiaDacSan = mutableStateListOf<LuotDanhGiaDacSan>()
    var luotDanhGiaDacSan by mutableStateOf<LuotDanhGiaDacSan?>(null)
    var diemDanhGia by mutableIntStateOf(0)
    var noiDung by mutableStateOf("")
    var yeuThich by mutableStateOf<Boolean?>(null)

    suspend fun docDanhSachDanhGia(): LuotDanhGiaDacSan? {
        val kq: Result<List<LuotDanhGiaDacSan>>

        try {
            dsDanhGiaDacSan.clear()
            kq = dacSanRepository.checkRate(dacSan.value!!.id)
            dsDanhGiaDacSan.addAll(kq.getOrNull() ?: emptyList())
        } catch (_: Exception) {
            dsDanhGiaDacSan.addAll(emptyList())
        }

        return luotDanhGiaDacSan
    }

    private suspend fun docDanhGia(): LuotDanhGiaDacSan? {
        val kq: Result<LuotDanhGiaDacSan>

        try {
            kq = dacSanRepository.checkRate(dacSan.value!!.id, nguoiDung!!.id)
            luotDanhGiaDacSan = kq.getOrNull()
        } catch (_: Exception) {
            luotDanhGiaDacSan = LuotDanhGiaDacSan()
        }

        return luotDanhGiaDacSan
    }

    private suspend fun docHinhAnh() {
        try {
            val kq = hinhAnhRepository.docTheoDacSan(dacSan.value!!.id)
            dsHinhAnh.clear()
            dsHinhAnh.addAll(kq.getOrNull() ?: emptyList())
        } catch (_: Exception) {
        }
    }

    private suspend fun docDacSan(id: Int) {
        dacSan.value = null
        try {
            if (nguoiDung != null) {
                val kq = dacSanRepository.xem(id, nguoiDung!!.id)
                dacSan.value = kq.getOrNull()
            } else {
                val kq = dacSanRepository.xem(id)
                dacSan.value = kq.getOrNull()
            }
        } catch (_: Exception) {
        }

    }

    suspend fun docDuLieu(id: Int) {
        if (id != (dacSan.value?.id ?: -1)) {
            viewModelScope.launch {
                loading.value = true
                val job = viewModelScope.launch {
                    docDacSan(id)
                }
                job.join()
                val job2 = viewModelScope.launch {
                    docDanhGia()
                    docHinhAnh()
                }
                job2.join()
                loading.value = false
            }
        }
    }

    suspend fun danhGia(): Boolean {
        luotDanhGiaDacSan = LuotDanhGiaDacSan(
            id_dac_san = dacSan.value!!.id,
            id_nguoi_dung = nguoiDung!!.id,
            diem_danh_gia = diemDanhGia,
            noi_dung = noiDung
        )

        val kq: Result<Boolean>

        return try {
            kq = dacSanRepository.rate(luotDanhGiaDacSan!!)
            kq.getOrNull() == true
        } catch (_: Exception) {
            false
        }
    }

    suspend fun like(id: Int): Boolean {
        val kq: Result<Boolean>

        try {
            kq = dacSanRepository.like(id, nguoiDung!!.id)
            yeuThich = kq.getOrNull() == true
        } catch (_: Exception) {
            yeuThich = false
        }

        return yeuThich!!
    }

    suspend fun unlike(id: Int): Boolean {
        val kq: Result<Boolean>

        try {
            kq = dacSanRepository.unlike(id, nguoiDung!!.id)
            yeuThich = kq.getOrNull() == false
        } catch (_: Exception) {
            yeuThich = true
        }

        return yeuThich!!
    }

    suspend fun checkLike(id: Int): Boolean {
        return if (yeuThich != null) {
            yeuThich!!
        } else {
            val kq: Result<Boolean>

            try {
                kq = dacSanRepository.checkLike(id, nguoiDung!!.id)
                yeuThich = kq.getOrNull() == true
            } catch (_: Exception) {
                yeuThich = false
            }

            yeuThich!!
        }
    }
}
