package com.example.appcsn.features.noiban.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.appcsn.core.ui.viewmodel.BaseViewModel
import com.example.appcsn.features.noiban.data.LuotDanhGiaNoiBan
import com.example.appcsn.features.noiban.data.NoiBan
import com.example.appcsn.features.noiban.domain.repository.NoiBanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrangChiTietNoiBanViewModel @Inject constructor(
    private val repository: NoiBanRepository,
) : BaseViewModel() {
    var noiBan = mutableStateOf<NoiBan?>(null)
    var luotDanhGia by mutableStateOf<LuotDanhGiaNoiBan?>(null)
    var diemDanhGia by mutableIntStateOf(0)
    var noiDung by mutableStateOf("")
    var yeuThich by mutableStateOf<Boolean?>(null)

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
                    checkLike(id)
                }
                job2.join()
                loading.value = false
            }
        }
    }

    private suspend fun docNoiBan(id: Int) {
        noiBan.value = null
        try {
            if (nguoiDung != null) {
                val kq = repository.xem(id, nguoiDung!!.id)
                noiBan.value = kq.getOrNull()
            } else {
                val kq = repository.xem(id)
                noiBan.value = kq.getOrNull()
            }
        } catch (_: Exception) {
        }

    }

    private suspend fun docDanhGia(): LuotDanhGiaNoiBan? {
        val kq: Result<LuotDanhGiaNoiBan>

        try {
            kq = repository.checkRate(noiBan.value!!.id, nguoiDung!!.id)
            luotDanhGia = kq.getOrNull()
        } catch (_: Exception) {
            luotDanhGia = LuotDanhGiaNoiBan()
        }

        return luotDanhGia
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
            kq = repository.rate(luotDanhGia!!)
            kq.getOrNull() == true
        } catch (_: Exception) {
            false
        }
    }

    suspend fun like(id: Int): Boolean {
        val kq: Result<Boolean>

        try {
            kq = repository.like(id, nguoiDung!!.id)
            yeuThich = kq.getOrNull() == true
        } catch (_: Exception) {
            yeuThich = false
        }

        return yeuThich!!
    }

    suspend fun unlike(id: Int): Boolean {
        val kq: Result<Boolean>

        try {
            kq = repository.unlike(id, nguoiDung!!.id)
            yeuThich = kq.getOrNull() == false
        } catch (_: Exception) {
            yeuThich = true
        }

        return yeuThich!!
    }

    private suspend fun checkLike(id: Int): Boolean {
        return if (yeuThich != null) {
            yeuThich!!
        } else {
            val kq: Result<Boolean>

            try {
                kq = repository.checkLike(id, nguoiDung!!.id)
                yeuThich = kq.getOrNull() == true
            } catch (_: Exception) {
                yeuThich = false
            }

            yeuThich!!
        }
    }
}
