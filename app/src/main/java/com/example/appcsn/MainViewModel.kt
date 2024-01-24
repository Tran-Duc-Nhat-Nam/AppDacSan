package com.example.appcsn

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.appcsn.models.DacSan
import com.example.appcsn.models.NoiBan

class MainViewModel : ViewModel() {
    val dsDacSan = mutableStateListOf<DacSan>()
    val dsNoiBan = mutableStateListOf<NoiBan>()
    val apiDacSan =
        HttpHelper.DacSanAPI.getInstance().create(DacSan.doc::class.java)
    val apiNoiBan =
        HttpHelper.DacSanAPI.getInstance().create(NoiBan.doc::class.java)

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
}