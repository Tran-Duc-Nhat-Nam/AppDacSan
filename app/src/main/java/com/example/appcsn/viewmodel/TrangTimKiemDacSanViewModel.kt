package com.example.appcsn.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.appcsn.data.model.DacSan
import com.example.appcsn.data.model.DanhSachMuaDacSan
import com.example.appcsn.data.model.DanhSachNguyenLieu
import com.example.appcsn.data.model.DanhSachVungMien
import com.example.appcsn.data.repository.DacSanRepository
import com.example.appcsn.domain.repository.BasePaginationRepository
import com.example.appcsn.ui.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrangTimKiemDacSanViewModel @Inject constructor(
    private val repository: DacSanRepository
) : BaseViewModel() {
    var state by mutableStateOf(ScreenState())
    private val paginator = BasePaginationRepository(
        initKey = state.pageIndex,
        onLoading = {
            state = state.copy(isLoading = it)
        },
        onRequest = { nextPage: Int ->
            repository.docTheoTrang(2, nextPage)
        },
        getNextKey = {
            state.pageIndex + 1
        },
        onError = {
            state = state.copy(errorMessage = it?.localizedMessage)
        },
        onSuccess = { items, newKey ->
            state = state.copy(ds = state.ds + items, pageIndex = newKey, isEnd = items.isEmpty())
        }
    )
    val dsDacSan = mutableStateListOf<DacSan>()
    var ten = ""
    var dsVungMien = DanhSachVungMien()
    var dsMuaDacSan = DanhSachMuaDacSan()
    var dsNguyenLieu = DanhSachNguyenLieu()

//    init {
//        loadNext()
//    }

    suspend fun docDuLieu() {
        dsDacSan.addAll(locKetQua(repository.docTheoTen(ten).getOrNull() ?: emptyList()))
    }

    fun loadNext() {
        viewModelScope.launch {
            paginator.loadNext()
        }
    }

    private fun locKetQua(
        ds: List<DacSan>,
    ): MutableList<DacSan> {
        val dsDacSanDaLoc = mutableListOf<DacSan>()

        if (dsVungMien.ds.isEmpty() && dsMuaDacSan.ds.isEmpty() && dsNguyenLieu.ds.isEmpty()) {
            dsDacSanDaLoc.addAll(ds)
        }

        if (dsVungMien.ds.isNotEmpty()) {
            for (vungMien in dsVungMien.ds) {
                dsDacSanDaLoc.addAll(ds.filter { e -> e.vung_mien.any { x -> x.id == vungMien.id } })
            }
        }

        if (dsMuaDacSan.ds.isNotEmpty()) {
            for (muaDacSan in dsMuaDacSan.ds) {
                dsDacSanDaLoc.addAll(ds.filter { e -> e.mua_dac_san.any { x -> x.id == muaDacSan.id } })
            }
        }

        if (dsNguyenLieu.ds.isNotEmpty()) {
            for (nguyenLieu in dsNguyenLieu.ds) {
                dsDacSanDaLoc.addAll(ds.filter { e -> e.thanh_phan.any { x -> x.nguyen_lieu.id == nguyenLieu.id } })
            }
        }

        return dsDacSanDaLoc
    }
}
