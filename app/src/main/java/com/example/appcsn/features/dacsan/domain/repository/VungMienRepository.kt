package com.example.appcsn.features.dacsan.domain.repository

import com.example.appcsn.features.dacsan.data.VungMien
import com.example.appcsn.features.dacsan.domain.api.VungMienAPI

class VungMienRepository(
    private val api: VungMienAPI
) {
    suspend fun doc(): Result<List<VungMien>> {
        val kq = api.doc()
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Đọc dữ liệu thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }
}
