package com.example.appcsn.data.repository

import com.example.appcsn.data.model.NoiBan
import com.example.appcsn.data.remote.NoiBanAPI

class NoiBanRepository(
    private val api: NoiBanAPI
) {
    suspend fun docDanhSach(): Result<List<NoiBan>> {
        val kq = api.docDanhSach()
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Đọc dữ liệu thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }
}
