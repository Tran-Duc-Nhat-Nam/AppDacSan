package com.example.appcsn.features.noiban.domain.repository

import com.example.appcsn.features.noiban.data.NoiBan
import com.example.appcsn.features.noiban.domain.api.YeuThichNoiBanAPI

class YeuThichNoiBanRepository(
    private val api: YeuThichNoiBanAPI
) {
    suspend fun yeuThich(idNoiBan: Int, idNguoiDung: String): Result<Boolean> {
        val kq = api.yeuThich(idNoiBan, idNguoiDung)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Yêu thích thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }

    suspend fun huyYeuThich(idNoiBan: Int, idNguoiDung: String): Result<Boolean> {
        val kq = api.huyYeuThich(idNoiBan, idNguoiDung)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Bỏ yêu thích thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }

    suspend fun kiemTraYeuThich(idNoiBan: Int, idNguoiDung: String): Result<Boolean> {
        val kq = api.kiemTraYeuThich(idNoiBan, idNguoiDung)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Kiểm tra yêu thích thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }

    suspend fun docDanhSachYeuThich(id: String): Result<List<NoiBan>> {
        val kq = api.docDanhSachYeuThich(id)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Đọc dữ liệu thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }
}