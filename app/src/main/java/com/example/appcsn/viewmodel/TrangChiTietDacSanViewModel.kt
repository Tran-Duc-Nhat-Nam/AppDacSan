package com.example.appcsn.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.appcsn.data.model.dacsan.DacSan
import com.example.appcsn.data.model.dacsan.LuotDanhGiaDacSan
import com.example.appcsn.data.repository.DacSanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrangChiTietDacSanViewModel @Inject constructor(
    private val dacSanRepository: DacSanRepository
) : BaseViewModel() {
    var dacSan: DacSan? = null
    var luotDanhGiaDacSan by mutableStateOf<LuotDanhGiaDacSan?>(null)
    var diemDanhGia by mutableIntStateOf(0)
    var noiDung by mutableStateOf("")
    var yeuThich by mutableStateOf<Boolean?>(null)

    suspend fun docDanhGia(): LuotDanhGiaDacSan? {
        val kq: Result<LuotDanhGiaDacSan>

        try {
            kq = dacSanRepository.checkRate(dacSan!!.id, nguoiDung!!.id)
            luotDanhGiaDacSan = kq.getOrNull()
        } catch (_: Exception) {
            luotDanhGiaDacSan = LuotDanhGiaDacSan()
        }

        return luotDanhGiaDacSan
    }

    suspend fun danhGia(): Boolean {
        luotDanhGiaDacSan = LuotDanhGiaDacSan(
            id_dac_san = dacSan!!.id,
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
