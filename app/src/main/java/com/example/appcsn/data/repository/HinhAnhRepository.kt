package com.example.appcsn.data.repository

import com.example.appcsn.data.model.HinhAnh
import com.example.appcsn.data.remote.HinhAnhAPI

class HinhAnhRepository(
    private val api: HinhAnhAPI
) {
    suspend fun docTheoDacSan(id: Int): Result<List<HinhAnh>> {
        val kq = api.doc(id)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Đọc dữ liệu thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }
}