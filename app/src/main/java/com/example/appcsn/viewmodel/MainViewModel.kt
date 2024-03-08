package com.example.appcsn.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.example.appcsn.data.model.MuaDacSan
import com.example.appcsn.data.model.VungMien
import com.example.appcsn.data.model.dacsan.DacSan
import com.example.appcsn.data.model.dacsan.TuKhoaTimKiem
import com.example.appcsn.data.repository.DacSanRepository
import com.example.appcsn.data.repository.MuaDacSanRepository
import com.example.appcsn.data.repository.TinhThanhRepository
import com.example.appcsn.data.repository.VungMienRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dacSanRepository: DacSanRepository,
    private val vungMienRepository: VungMienRepository,
    private val muaDacSanRepository: MuaDacSanRepository,
    private val tinhThanhRepository: TinhThanhRepository
) : BaseViewModel() {
    var dsDacSan = mutableStateListOf<DacSan>()
    var dsVungMien = mutableStateListOf<VungMien>()
    var dsMuaDacSan = mutableStateListOf<MuaDacSan>()
    var dsChonVungMien = mutableStateListOf<Boolean>()
    var dsChonMuaDacSan = mutableStateListOf<Boolean>()

    fun initAuth() {
        docDuLieu()
    }

    private fun docDuLieu() {
        viewModelScope.launch {
            val kqVM = vungMienRepository.doc()
            dsVungMien.clear()
            dsVungMien.addAll(kqVM.getOrNull() ?: emptyList())
            dsChonVungMien.clear()
            for (vungMien in dsVungMien) {
                dsChonVungMien.add(false)
            }
            val kqMDS = muaDacSanRepository.doc()
            dsMuaDacSan.clear()
            dsMuaDacSan.addAll(kqMDS.getOrNull() ?: emptyList())
            dsChonMuaDacSan.clear()
            for (muaDacSan in dsMuaDacSan) {
                dsChonMuaDacSan.add(false)
            }
        }
    }

    fun taoTuKhoa(): TuKhoaTimKiem {
        val tuKhoa = TuKhoaTimKiem()

        tuKhoa.dsVungMien.clear()
        for (i in 0 until dsVungMien.size) {
            if (dsChonVungMien[i]) {
                tuKhoa.dsVungMien.add(dsVungMien[i].id)
            }
        }

        tuKhoa.dsMuaDacSan.clear()
        for (i in 0 until dsMuaDacSan.size) {
            if (dsChonMuaDacSan[i]) {
                tuKhoa.dsMuaDacSan.add(dsMuaDacSan[i].id)
            }
        }

        return tuKhoa
    }

    fun goiY(ten: String) {
        viewModelScope.launch {
            val kq = dacSanRepository.timKiem(ten, TuKhoaTimKiem(), 10, 0)
            dsDacSan.clear()
            dsDacSan.addAll(kq.getOrNull() ?: emptyList())
        }
    }

    suspend fun checkConnect(): Boolean {
        return try {
            tinhThanhRepository.docTheoID(1)
            true
        } catch (e: Exception) {
            false
        }
    }
}
