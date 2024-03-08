package com.example.appcsn.data.repository

import com.example.appcsn.data.model.MuaDacSan
import com.example.appcsn.data.remote.MuaDacSanAPI

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