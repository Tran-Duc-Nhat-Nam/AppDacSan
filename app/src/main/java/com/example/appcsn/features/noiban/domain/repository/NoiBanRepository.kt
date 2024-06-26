package com.example.appcsn.features.noiban.domain.repository

import com.example.appcsn.features.noiban.data.NoiBan
import com.example.appcsn.features.noiban.domain.api.NoiBanAPI

class NoiBanRepository(
    private val api: NoiBanAPI
) {
    suspend fun doc(): Result<List<NoiBan>> {
        val kq = api.doc()
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Đọc dữ liệu thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }

    suspend fun timKiem(ten: String, pageSize: Int, pageIndex: Int): Result<List<NoiBan>> {
        val kq = api.timKiem(ten, pageSize, pageIndex)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Đọc dữ liệu thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }
}
