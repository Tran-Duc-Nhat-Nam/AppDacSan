package com.example.appcsn.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
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
    val dsYeuThich = mutableStateMapOf<Int, Boolean>()

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
            for (noiBan in dsNoiBan) {
                checkLike(noiBan.id)
            }
        }

    }

    suspend fun like(id: Int): Boolean {
        val kq: Result<Boolean>

        try {
            kq = repository.like(id, nguoiDung!!.id)
            dsYeuThich[id] = kq.getOrNull() == true
        } catch (_: Exception) {
            dsYeuThich[id] = false
        }

        return dsYeuThich[id]!!
    }

    suspend fun unlike(id: Int): Boolean {
        val kq: Result<Boolean>

        try {
            kq = repository.unlike(id, nguoiDung!!.id)
            dsYeuThich[id] = kq.getOrNull() == false
        } catch (_: Exception) {
            dsYeuThich[id] = true
        }

        return dsYeuThich[id]!!
    }

    private suspend fun checkLike(id: Int): Boolean {
        return if (dsYeuThich[id] != null) {
            dsYeuThich[id]!!
        } else {
            val kq: Result<Boolean>

            try {
                kq = repository.checkLike(id, nguoiDung!!.id)
                dsYeuThich[id] = kq.getOrNull() == true
            } catch (_: Exception) {
                dsYeuThich[id] = false
            }

            dsYeuThich[id]!!
        }
    }
}
