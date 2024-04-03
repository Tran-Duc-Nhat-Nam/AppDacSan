package com.example.appcsn.features.dacsan.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.appcsn.core.data.HinhAnh
import com.example.appcsn.core.domain.repository.HinhAnhRepository
import com.example.appcsn.core.ui.viewmodel.BaseViewModel
import com.example.appcsn.features.dacsan.data.DacSan
import com.example.appcsn.features.dacsan.data.LuotDanhGiaDacSan
import com.example.appcsn.features.dacsan.domain.repository.DanhGiaDacSanRepository
import com.example.appcsn.features.dacsan.domain.repository.XemDacSanRepository
import com.example.appcsn.features.dacsan.domain.repository.YeuThichDacSanRepository
import com.example.appcsn.features.nguoidung.domain.repository.NguoiDungRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrangChiTietDacSanViewModel @Inject constructor(
    private val xemRepository: XemDacSanRepository,
    private val danhGiaRepository: DanhGiaDacSanRepository,
    private val yeuThichRepository: YeuThichDacSanRepository,
    private val hinhAnhRepository: HinhAnhRepository,
    private val nguoiDungRepository: NguoiDungRepository
) : BaseViewModel() {
    var dacSan = mutableStateOf<DacSan?>(null)
    var dsHinhAnh = mutableStateListOf<HinhAnh>()
    var dsDanhGiaDacSan = mutableStateMapOf<LuotDanhGiaDacSan, String>()
    var luotDanhGiaDacSan by mutableStateOf<LuotDanhGiaDacSan?>(null)
    var diemDanhGia by mutableIntStateOf(0)
    var noiDung by mutableStateOf("")
    var yeuThich by mutableStateOf<Boolean?>(null)

    suspend fun docDanhSachDanhGia(): LuotDanhGiaDacSan? {
        val kq: Result<List<LuotDanhGiaDacSan>>

        try {
            dsDanhGiaDacSan.clear()
            kq = danhGiaRepository.doc(dacSan.value!!.id)
            val temp = kq.getOrNull() ?: emptyList()
            for (item in temp) {
                if (item.id_nguoi_dung != (nguoiDung?.id ?: "null")) {
                    val ten = nguoiDungRepository.doc(item.id_nguoi_dung)
                    dsDanhGiaDacSan[item] = ten.getOrNull()?.ten ?: "Người dùng ẩn danh"
                }
            }
        } catch (_: Exception) {

        }

        return luotDanhGiaDacSan
    }

    private suspend fun docDanhGia(): LuotDanhGiaDacSan? {
        val kq: Result<LuotDanhGiaDacSan>

        try {
            kq = danhGiaRepository.doc(dacSan.value!!.id, nguoiDung!!.id)
            luotDanhGiaDacSan = kq.getOrNull()
            if (luotDanhGiaDacSan != null) {
                diemDanhGia = luotDanhGiaDacSan!!.diem_danh_gia
                noiDung = luotDanhGiaDacSan!!.noi_dung!!
            }
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
                val kq = xemRepository.xem(id, nguoiDung!!.id)
                dacSan.value = kq.getOrNull()
            } else {
                val kq = xemRepository.xem(id)
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
                    kiemTraYeuThich(id)
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
            kq = danhGiaRepository.danhGia(luotDanhGiaDacSan!!)
            kq.getOrNull() == true
        } catch (_: Exception) {
            false
        }
    }

    suspend fun capNhatDanhGia(): Boolean {
        luotDanhGiaDacSan = LuotDanhGiaDacSan(
            id_dac_san = dacSan.value!!.id,
            id_nguoi_dung = nguoiDung!!.id,
            diem_danh_gia = diemDanhGia,
            noi_dung = noiDung
        )

        val kq: Result<Boolean>

        return try {
            kq = danhGiaRepository.capNhatDanhGia(luotDanhGiaDacSan!!)
            kq.getOrNull() == true
        } catch (_: Exception) {
            false
        }
    }

    suspend fun huyDanhGia(): Boolean {
        val kq: Result<Boolean>

        return if (nguoiDung == null) {
            false
        } else {
            try {
                kq = danhGiaRepository.huyDanhGia(
                    luotDanhGiaDacSan!!.id_dac_san,
                    luotDanhGiaDacSan!!.id_nguoi_dung
                )
                kq.getOrNull() == true
            } catch (_: Exception) {
                false
            }
        }
    }

    suspend fun yeuThich(id: Int): Boolean {
        val kq: Result<Boolean>

        try {
            kq = yeuThichRepository.yeuThich(id, nguoiDung!!.id)
            yeuThich = kq.getOrNull() == true
        } catch (_: Exception) {
            yeuThich = false
        }

        return yeuThich!!
    }

    suspend fun huyYeuThich(id: Int): Boolean {
        val kq: Result<Boolean>

        try {
            kq = yeuThichRepository.huyYeuThich(id, nguoiDung!!.id)
            yeuThich = kq.getOrNull() == false
        } catch (_: Exception) {
            yeuThich = true
        }

        return yeuThich!!
    }

    private suspend fun kiemTraYeuThich(id: Int): Boolean {
        return if (yeuThich != null) {
            yeuThich!!
        } else {
            val kq: Result<Boolean>

            try {
                kq = yeuThichRepository.kiemTraYeuThich(id, nguoiDung!!.id)
                yeuThich = kq.getOrNull() == true
            } catch (_: Exception) {
                yeuThich = false
            }

            yeuThich!!
        }
    }
}
