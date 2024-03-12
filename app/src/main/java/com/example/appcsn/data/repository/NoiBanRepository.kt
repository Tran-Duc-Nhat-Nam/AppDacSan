package com.example.appcsn.data.repository

import com.example.appcsn.data.model.noiban.LuotDanhGiaNoiBan
import com.example.appcsn.data.model.noiban.NoiBan
import com.example.appcsn.data.remote.NoiBanAPI

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

    suspend fun like(idDacSan: Int, idNguoiDung: String): Result<Boolean> {
        val kq = api.like(idDacSan, idNguoiDung)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Yêu thích thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }

    suspend fun unlike(idDacSan: Int, idNguoiDung: String): Result<Boolean> {
        val kq = api.unlike(idDacSan, idNguoiDung)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Bỏ yêu thích thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }

    suspend fun checkLike(idDacSan: Int, idNguoiDung: String): Result<Boolean> {
        val kq = api.checkLike(idDacSan, idNguoiDung)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Kiểm tra yêu thích thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }

    suspend fun rate(luotDanhGia: LuotDanhGiaNoiBan): Result<Boolean> {
        val kq = api.danhGia(luotDanhGia.id_noi_ban, luotDanhGia)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Đánh giá thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }

    suspend fun checkRate(idNoiBan: Int, idNguoiDung: String): Result<LuotDanhGiaNoiBan> {
        val kq = api.docDanhGia(idNoiBan, idNguoiDung)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Kiểm tra đánh giá thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }
}
