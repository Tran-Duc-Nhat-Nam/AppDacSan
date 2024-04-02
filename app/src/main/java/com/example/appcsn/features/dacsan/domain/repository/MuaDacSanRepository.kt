package com.example.appcsn.features.dacsan.domain.repository

import com.example.appcsn.features.dacsan.data.MuaDacSan
import com.example.appcsn.features.dacsan.domain.api.MuaDacSanAPI

class MuaDacSanRepository(
    private val api: MuaDacSanAPI
) {
    suspend fun doc(): Result<List<MuaDacSan>> {
        val kq = api.doc()
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Đọc dữ liệu thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }
}