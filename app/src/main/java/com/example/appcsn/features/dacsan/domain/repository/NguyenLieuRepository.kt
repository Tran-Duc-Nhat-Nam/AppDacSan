package com.example.appcsn.features.dacsan.domain.repository

import com.example.appcsn.features.dacsan.data.NguyenLieu
import com.example.appcsn.features.dacsan.domain.api.NguyenLieuAPI

class NguyenLieuRepository(
    private val api: NguyenLieuAPI
) {
    suspend fun doc(): Result<List<NguyenLieu>> {
        val kq = api.doc()
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Đọc dữ liệu thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }

    suspend fun doc(ten: String, pageSize: Int, pageIndex: Int): Result<List<NguyenLieu>> {
        val kq = api.doc(ten, pageSize, pageIndex)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Đọc dữ liệu thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }
}