package com.example.appcsn.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.appcsn.HttpHelper
import com.example.appcsn.models.DacSan
import com.example.appcsn.models.NoiBan
import com.example.appcsn.models.TinhThanh
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainViewModel : BaseViewModel() {
    private lateinit var auth: FirebaseAuth
    var loading = mutableStateOf(true)
    val dsDacSan = mutableStateListOf<DacSan>()
    val dsNoiBan = mutableStateListOf<NoiBan>()
    private val apiDacSan: DacSan.doc =
        HttpHelper.DacSanAPI.getInstance().create(DacSan.doc::class.java)
    private val apiNoiBan: NoiBan.doc =
        HttpHelper.DacSanAPI.getInstance().create(NoiBan.doc::class.java)

    init {
//        viewModelScope.launch {
//            docDuLieu()
//        }
    }

    suspend fun docDuLieu() {
        val kq = apiDacSan.doc()

        if (kq.body() != null) {
            dsDacSan.addAll(kq.body()!!)
        }

        val kq2 = apiNoiBan.doc()

        if (kq2.body() != null) {
            dsNoiBan.addAll(kq2.body()!!)
        }
    }

    fun initAuth() {
        auth = Firebase.auth
    }

    suspend fun checkConnect(): Boolean {
        return try {
            HttpHelper.DacSanAPI.getInstance().create(TinhThanh.docAPI::class.java).docTheoID(1)
            true
        } catch (e: Exception) {
            false
        }
    }
}
