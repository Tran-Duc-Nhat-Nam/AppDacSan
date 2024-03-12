package com.example.appcsn.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.appcsn.data.model.noiban.LuotDanhGiaNoiBan
import com.example.appcsn.data.model.noiban.NoiBan
import com.example.appcsn.data.repository.HinhAnhRepository
import com.example.appcsn.data.repository.NoiBanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrangChiTietNoiBanViewModel @Inject constructor(
    private val noiBanRepository: NoiBanRepository,
    private val hinhAnhRepository: HinhAnhRepository
) : BaseViewModel() {
    var noiBan: NoiBan? = null
    var luotDanhGia by mutableStateOf<LuotDanhGiaNoiBan?>(null)
    var diemDanhGia by mutableIntStateOf(0)
    var noiDung by mutableStateOf("")
    var yeuThich by mutableStateOf<Boolean?>(null)

    suspend fun docDanhGia(): LuotDanhGiaNoiBan? {
        val kq: Result<LuotDanhGiaNoiBan>

        try {
            kq = noiBanRepository.checkRate(noiBan!!.id, nguoiDung!!.id)
            luotDanhGia = kq.getOrNull()
        } catch (_: Exception) {
            luotDanhGia = LuotDanhGiaNoiBan()
        }

        return luotDanhGia
    }

    suspend fun danhGia(): Boolean {
        luotDanhGia = LuotDanhGiaNoiBan(
            id_noi_ban = noiBan!!.id,
            id_nguoi_dung = nguoiDung!!.id,
            diem_danh_gia = diemDanhGia,
            noi_dung = noiDung
        )

        val kq: Result<Boolean>

        return try {
            kq = noiBanRepository.rate(luotDanhGia!!)
            kq.getOrNull() == true
        } catch (_: Exception) {
            false
        }
    }

    suspend fun like(id: Int): Boolean {
        val kq: Result<Boolean>

        try {
            kq = noiBanRepository.like(id, nguoiDung!!.id)
            yeuThich = kq.getOrNull() == true
        } catch (_: Exception) {
            yeuThich = false
        }

        return yeuThich!!
    }

    suspend fun unlike(id: Int): Boolean {
        val kq: Result<Boolean>

        try {
            kq = noiBanRepository.unlike(id, nguoiDung!!.id)
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
                kq = noiBanRepository.checkLike(id, nguoiDung!!.id)
                yeuThich = kq.getOrNull() == true
            } catch (_: Exception) {
                yeuThich = false
            }

            yeuThich!!
        }
    }
}
