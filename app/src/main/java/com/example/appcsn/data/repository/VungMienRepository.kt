package com.example.appcsn.data.repository

import com.example.appcsn.data.model.VungMien
import com.example.appcsn.data.remote.VungMienAPI

class VungMienRepository(
    private val api: VungMienAPI
) {
    suspend fun docDanhSach(): Result<List<VungMien>> {
        val kq = api.docDanhSach()
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Đọc dữ liệu thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }
}
