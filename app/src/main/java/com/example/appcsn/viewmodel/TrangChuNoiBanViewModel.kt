package com.example.appcsn.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.example.appcsn.data.model.noiban.NoiBan
import com.example.appcsn.data.repository.NoiBanRepository
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
        val kq = repository.doc()

        if (kq.getOrNull() != null) {
            dsNoiBan.addAll(kq.getOrNull()!!)
            Log.d("Data", "Nơi bán: ${dsNoiBan.size}")
        }

    }
}
