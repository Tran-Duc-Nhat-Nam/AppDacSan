package com.example.appcsn.core.domain.repository

import com.example.appcsn.core.data.QuanHuyen
import com.example.appcsn.core.domain.api.QuanHuyenAPI

class QuanHuyenRepository(
    private val api: QuanHuyenAPI
) {
    suspend fun docTheoID(id: Int): Result<List<QuanHuyen>> {
        val kq = api.docDanhSach(id)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Đọc dữ liệu thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }
}
