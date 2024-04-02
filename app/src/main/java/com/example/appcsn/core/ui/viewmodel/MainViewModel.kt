package com.example.appcsn.core.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.example.appcsn.BuildConfig
import com.example.appcsn.core.domain.repository.TinhThanhRepository
import com.example.appcsn.features.dacsan.data.DacSan
import com.example.appcsn.features.dacsan.data.MuaDacSan
import com.example.appcsn.features.dacsan.data.NguyenLieu
import com.example.appcsn.features.dacsan.data.TuKhoaTimKiem
import com.example.appcsn.features.dacsan.data.VungMien
import com.example.appcsn.features.dacsan.domain.repository.DacSanRepository
import com.example.appcsn.features.dacsan.domain.repository.MuaDacSanRepository
import com.example.appcsn.features.dacsan.domain.repository.NguyenLieuRepository
import com.example.appcsn.features.dacsan.domain.repository.VungMienRepository
import com.example.appcsn.features.nguoidung.data.NguoiDung
import com.example.appcsn.features.nguoidung.domain.repository.NguoiDungRepository
import com.example.appcsn.features.noiban.data.NoiBan
import com.example.appcsn.features.noiban.domain.repository.NoiBanRepository
import com.google.ai.client.generativeai.GenerativeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dacSanRepository: DacSanRepository,
    private val noiBanRepository: NoiBanRepository,
    private val nguoiDungRepository: NguoiDungRepository,
    private val vungMienRepository: VungMienRepository,
    private val muaDacSanRepository: MuaDacSanRepository,
    private val nguyenLieuRepository: NguyenLieuRepository,
    private val tinhThanhRepository: TinhThanhRepository
) : BaseViewModel() {
    var dsDacSan = mutableStateListOf<DacSan>()
    var dsNoiBan = mutableStateListOf<NoiBan>()
    var dsNguoiDung = mutableStateListOf<NguoiDung>()
    var dsVungMien = mutableStateListOf<VungMien>()
    var dsMuaDacSan = mutableStateListOf<MuaDacSan>()
    var dsNguyenLieu = mutableStateListOf<NguyenLieu>()
    var dsChonVungMien = mutableStateListOf<Boolean>()
    var dsChonMuaDacSan = mutableStateListOf<Boolean>()
    var dsNguyenLieuDaChon = mutableStateListOf<NguyenLieu>()

    private val generativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = BuildConfig.apiKey
    )
    val chatInputs = mutableStateListOf<String>()
    val chatOutputs = mutableStateListOf<String>()

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

        tuKhoa.dsNguyenLieu.clear()
        for (i in 0 until dsNguyenLieuDaChon.size) {
            tuKhoa.dsMuaDacSan.add(dsNguyenLieuDaChon[i].id)
        }

        return tuKhoa
    }

    fun goiYDacSan(ten: String) {
        viewModelScope.launch {
            val kq = dacSanRepository.timKiem(ten, TuKhoaTimKiem(), 10, 0)
            dsDacSan.clear()
            dsDacSan.addAll(kq.getOrNull() ?: emptyList())
        }
    }

    fun goiYNoiBan(ten: String) {
        viewModelScope.launch {
            val kq = noiBanRepository.timKiem(ten, 10, 0)
            dsNoiBan.clear()
            dsNoiBan.addAll(kq.getOrNull() ?: emptyList())
        }
    }

    fun goiYNguoiDung(ten: String) {
        viewModelScope.launch {
            val kq = nguoiDungRepository.timKiem(ten, 10, 0)
            dsNguoiDung.clear()
            dsNguoiDung.addAll(kq.getOrNull() ?: emptyList())
        }
    }


    fun goiYNguyenLieu(ten: String) {
        viewModelScope.launch {
            val kq = nguyenLieuRepository.doc(ten, 10, 0)
            dsNguyenLieu.clear()
            dsNguyenLieu.addAll(kq.getOrNull() ?: emptyList())
        }
    }

    suspend fun checkConnect(): Boolean {
        loading.value = true
        return try {
            tinhThanhRepository.docTheoID(1)
            loading.value = false
            true
        } catch (e: Exception) {
            loading.value = false
            false
        }
    }

    suspend fun chat() {
        try {

            chatOutputs.add(
                generativeModel.generateContent(
                    chatInputs.last()
                ).text ?: "Không có phản hồi"
            )

        } catch (e: Exception) {
            chatOutputs.add(
                e.localizedMessage
                    ?: "Đã xảy ra lỗi. Vui lòng thử lại."
            )
        }

    }
}
