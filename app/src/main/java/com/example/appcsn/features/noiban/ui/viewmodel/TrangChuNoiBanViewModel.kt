package com.example.appcsn.features.noiban.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.example.appcsn.core.ui.viewmodel.BaseViewModel
import com.example.appcsn.features.noiban.data.NoiBan
import com.example.appcsn.features.noiban.domain.repository.NoiBanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrangChuNoiBanViewModel @Inject constructor(
    private val repository: NoiBanRepository
) : BaseViewModel() {
    var dsNoiBan = mutableStateListOf<NoiBan>()

    init {
        viewModelScope.launch {
            loading.value = true
            val job = viewModelScope.launch {
                docDuLieu()
            }
            job.join()
            loading.value = false
        }
    }

    private suspend fun docDuLieu() {
        val kq = repository.timKiem("", 2, 0)

        if (kq.getOrNull() != null) {
            dsNoiBan.addAll(kq.getOrNull()!!)
        }
    }
}
