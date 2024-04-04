package com.example.appcsn.features.noiban.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.appcsn.core.ui.viewmodel.BaseViewModel
import com.example.appcsn.features.nguoidung.domain.repository.NguoiDungRepository
import com.example.appcsn.features.noiban.data.LuotDanhGiaNoiBan
import com.example.appcsn.features.noiban.data.NoiBan
import com.example.appcsn.features.noiban.domain.repository.DanhGiaNoiBanRepository
import com.example.appcsn.features.noiban.domain.repository.XemNoiBanRepository
import com.example.appcsn.features.noiban.domain.repository.YeuThichNoiBanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrangChiTietNoiBanViewModel @Inject constructor(
    private val xemRepository: XemNoiBanRepository,
    private val danhGiaRepository: DanhGiaNoiBanRepository,
    private val yeuThichRepository: YeuThichNoiBanRepository,
    private val nguoiDungRepository: NguoiDungRepository
) : BaseViewModel() {
    var noiBan = mutableStateOf<NoiBan?>(null)
    var dsDanhGiaNoiBan = mutableStateMapOf<LuotDanhGiaNoiBan, String>()
    var luotDanhGia by mutableStateOf<LuotDanhGiaNoiBan?>(null)
    var diemDanhGia by mutableIntStateOf(0)
    var noiDung by mutableStateOf("")
    var yeuThich by mutableStateOf<Boolean?>(null)

    suspend fun docDanhSachDanhGia(): LuotDanhGiaNoiBan? {
        val kq: Result<List<LuotDanhGiaNoiBan>>

        try {
            dsDanhGiaNoiBan.clear()
            kq = danhGiaRepository.doc(noiBan.value!!.id)
            val temp = kq.getOrNull() ?: emptyList()
            for (item in temp) {
                if (item.id_nguoi_dung != (nguoiDung?.id ?: "null")) {
                    val ten = nguoiDungRepository.doc(item.id_nguoi_dung)
                    dsDanhGiaNoiBan[item] = ten.getOrNull()?.ten ?: "Người dùng ẩn danh"
                }
            }
        } catch (_: Exception) {

        }

        return luotDanhGia
    }

    private suspend fun docDanhGia(): LuotDanhGiaNoiBan? {
        val kq: Result<LuotDanhGiaNoiBan>

        try {
            kq = danhGiaRepository.doc(noiBan.value!!.id, nguoiDung!!.id)
            luotDanhGia = kq.getOrNull()
            if (luotDanhGia != null) {
                diemDanhGia = luotDanhGia!!.diem_danh_gia
                noiDung = luotDanhGia!!.noi_dung!!
            }
        } catch (_: Exception) {
            luotDanhGia = LuotDanhGiaNoiBan()
        }

        return luotDanhGia
    }

    private suspend fun docNoiBan(id: Int) {
        noiBan.value = null
        try {
            if (nguoiDung != null) {
                val kq = xemRepository.xem(id, nguoiDung!!.id)
                noiBan.value = kq.getOrNull()
            } else {
                val kq = xemRepository.xem(id)
                noiBan.value = kq.getOrNull()
            }
        } catch (_: Exception) {
        }

    }

    suspend fun docDuLieu(id: Int) {
        if (id != (noiBan.value?.id ?: -1)) {
            viewModelScope.launch {
                loading.value = true
                val job = viewModelScope.launch {
                    docNoiBan(id)
                }
                job.join()
                val job2 = viewModelScope.launch {
                    docDanhGia()
                    kiemTraYeuThich(id)
                }
                job2.join()
                loading.value = false
            }
        }
    }

    suspend fun danhGia(): Boolean {
        luotDanhGia = LuotDanhGiaNoiBan(
            id_noi_ban = noiBan.value!!.id,
            id_nguoi_dung = nguoiDung!!.id,
            diem_danh_gia = diemDanhGia,
            noi_dung = noiDung
        )

        val kq: Result<Boolean>

        return try {
            kq = danhGiaRepository.danhGia(luotDanhGia!!)
            kq.getOrNull() == true
        } catch (_: Exception) {
            false
        }
    }

    suspend fun capNhatDanhGia(): Boolean {
        luotDanhGia = LuotDanhGiaNoiBan(
            id_noi_ban = noiBan.value!!.id,
            id_nguoi_dung = nguoiDung!!.id,
            diem_danh_gia = diemDanhGia,
            noi_dung = noiDung
        )

        val kq: Result<Boolean>

        return try {
            kq = danhGiaRepository.capNhatDanhGia(luotDanhGia!!)
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
                    luotDanhGia!!.id_noi_ban,
                    luotDanhGia!!.id_nguoi_dung
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
