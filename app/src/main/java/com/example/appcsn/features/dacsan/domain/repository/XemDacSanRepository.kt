package com.example.appcsn.features.dacsan.domain.repository

import com.example.appcsn.features.dacsan.data.DacSan
import com.example.appcsn.features.dacsan.domain.api.XemDacSanAPI

class XemDacSanRepository(
    private val api: XemDacSanAPI
) {
    suspend fun xem(idDacSan: Int): Result<DacSan> {
        val kq = api.xem(idDacSan)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Đọc dữ liệu thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }

    suspend fun xem(idDacSan: Int, idNguoiDung: String): Result<DacSan> {
        val kq = api.xem(idDacSan, idNguoiDung)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Đọc dữ liệu thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }

    suspend fun docLichSuXem(idNguoiDung: String): Result<List<DacSan>> {
        val kq = api.docLichSuXem(idNguoiDung)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Kiểm tra đánh giá thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }
}