package com.example.appcsn.viewmodels

import androidx.compose.runtime.mutableStateListOf
import com.example.appcsn.HttpHelper
import com.example.appcsn.models.DacSan
import com.example.appcsn.models.DanhSachVungMien
import com.example.appcsn.models.MuaDacSan
import com.example.appcsn.models.NguyenLieu
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response

@HiltViewModel
class TrangTimKiemDacSanViewModel : BaseViewModel() {
    val dsDacSan = mutableStateListOf<DacSan>()
    private val apiDacSan: DacSan.doc =
        HttpHelper.DacSanAPI.getInstance().create(DacSan.doc::class.java)
    var ten = ""
    var dsVungMien = DanhSachVungMien()
    var dsMuaDacSan = listOf<MuaDacSan>()
    var dsNguyenLieu = listOf<NguyenLieu>()

    suspend fun docDuLieu() {
        dsDacSan.addAll(locKetQua(kq = apiDacSan.docTheoTen(ten)))
    }

    private fun locKetQua(
        kq: Response<List<DacSan>>,
    ): MutableList<DacSan> {
        val dsDacSanDaLoc = mutableListOf<DacSan>()

        var dsDacSanTemp = listOf<DacSan>()

        if (kq.body() != null) {
            dsDacSanTemp = kq.body()!!
        }

        for (vungMien in dsVungMien.ds) {
            dsDacSanDaLoc.addAll(dsDacSanTemp.filter { e -> e.vung_mien.any { x -> x.id == vungMien.id } })
        }

        for (muaDacSan in dsMuaDacSan) {
            dsDacSanDaLoc.addAll(dsDacSanTemp.filter { e -> e.mua_dac_san.any { x -> x.id == muaDacSan.id } })
        }

        for (nguyenLieu in dsNguyenLieu) {
            dsDacSanDaLoc.addAll(dsDacSanTemp.filter { e -> e.thanh_phan.any { x -> x.nguyen_lieu.id == nguyenLieu.id } })
        }

        if (dsVungMien.ds.isEmpty() && dsMuaDacSan.isEmpty() && dsNguyenLieu.isEmpty()) {
            dsDacSanDaLoc.addAll(dsDacSanTemp)
        }

        return dsDacSanDaLoc
    }
}
