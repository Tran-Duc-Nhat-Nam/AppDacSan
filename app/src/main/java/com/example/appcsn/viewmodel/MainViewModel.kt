package com.example.appcsn.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.example.appcsn.data.model.dacsan.DacSan
import com.example.appcsn.data.repository.DacSanRepository
import com.example.appcsn.data.repository.TinhThanhRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dacSanRepository: DacSanRepository,
    private val tinhThanhRepository: TinhThanhRepository
) : BaseViewModel() {
    private lateinit var auth: FirebaseAuth
    var dsDacSan = mutableStateListOf<DacSan>()

    fun initAuth() {
        auth = Firebase.auth
    }

    fun docDuLieu(ten: String) {
        viewModelScope.launch {
            val kq = dacSanRepository.docTheoTen(ten)
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
