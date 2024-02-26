package com.example.appcsn.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.appcsn.data.model.DanhSachMuaDacSan
import com.example.appcsn.data.model.DanhSachNguyenLieu
import com.example.appcsn.data.model.DanhSachVungMien
import com.example.appcsn.data.model.dacsan.DacSan
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
            try {
                Result.success(dsDacSan.subList(nextPage, nextPage + 1))
            } catch (e: Exception) {
                Result.failure(e)
            }
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

    fun docDuLieu() {
        viewModelScope.launch {
            loading.value = true
            val job = viewModelScope.launch {
                if (ten.isEmpty()) {
                    dsDacSan.addAll(locKetQua(repository.docDanhSach().getOrNull() ?: emptyList()))
                    Log.d("Load data", "${dsDacSan.size}")
                    paginator.loadNext()
                } else {
                    dsDacSan.addAll(
                        locKetQua(
                            repository.docTheoTen(ten).getOrNull() ?: emptyList()
                        )
                    )
                    Log.d("Load data", "${dsDacSan.size}")
                    paginator.loadNext()
                }
            }
            job.join()
            loading.value = false
        }
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

        for (dacSan in ds) {
            var isValid = true
            for (vungMien in dsVungMien.ds) {
                if (!dacSan.vung_mien.any { x -> x.id == vungMien.id }) {
                    isValid = false
                    break
                }
            }
            if (!isValid) {
                continue
            }
            for (muaDacSan in dsMuaDacSan.ds) {
                if (!dacSan.mua_dac_san.any { x -> x.id == muaDacSan.id }) {
                    isValid = false
                    break
                }
            }
            if (!isValid) {
                continue
            }
            for (nguyenLieu in dsNguyenLieu.ds) {
                if (!dacSan.thanh_phan.any { x -> x.nguyen_lieu.id == nguyenLieu.id }) {
                    isValid = false
                    break
                }
            }
            if (isValid) {
                dsDacSanDaLoc.add(dacSan)
            }
        }

        return dsDacSanDaLoc
    }
}
