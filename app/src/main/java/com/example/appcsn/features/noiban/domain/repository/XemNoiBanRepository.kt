package com.example.appcsn.features.noiban.domain.repository

import com.example.appcsn.features.noiban.data.NoiBan
import com.example.appcsn.features.noiban.domain.api.XemNoiBanAPI

class XemNoiBanRepository(
    private val api: XemNoiBanAPI
) {
    suspend fun xem(idDacSan: Int): Result<NoiBan> {
        val kq = api.xem(idDacSan)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Đọc dữ liệu thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }

    suspend fun xem(idDacSan: Int, idNguoiDung: String): Result<NoiBan> {
        val kq = api.xem(idDacSan, idNguoiDung)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Đọc dữ liệu thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }
}