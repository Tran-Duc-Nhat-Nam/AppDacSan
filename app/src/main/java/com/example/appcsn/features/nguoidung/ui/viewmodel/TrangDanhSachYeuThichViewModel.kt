package com.example.appcsn.features.nguoidung.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.example.appcsn.core.ui.viewmodel.BaseViewModel
import com.example.appcsn.features.dacsan.data.DacSan
import com.example.appcsn.features.dacsan.domain.repository.YeuThichDacSanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrangDanhSachYeuThichViewModel @Inject constructor(
    private val repository: YeuThichDacSanRepository
) : BaseViewModel() {
    var dsDacSan = mutableStateListOf<DacSan>()

    init {
        viewModelScope.launch {
            loading.value = true
            val job = viewModelScope.launch {
                docDuLieu(nguoiDung!!.id)
            }
            job.join()
            loading.value = false
        }
    }

    private suspend fun docDuLieu(id: String) {
        val kq = repository.docDanhSachYeuThich(id)

        if (kq.getOrNull() != null) {
            dsDacSan.addAll(kq.getOrNull()!!)
        }
    }
}