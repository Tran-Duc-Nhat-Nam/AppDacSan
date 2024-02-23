package com.example.appcsn.data.repository

import com.example.appcsn.data.model.TinhThanh
import com.example.appcsn.data.remote.TinhThanhAPI

class TinhThanhRepository(
    private val api: TinhThanhAPI
) {
    suspend fun docDanhSach(): Result<List<TinhThanh>> {
        val kq = api.docDanhSach()
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Đọc dữ liệu thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }

    suspend fun docTheoID(id: Int): Result<TinhThanh> {
        val kq = api.docTheoID(id)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Đọc dữ liệu thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }
}
