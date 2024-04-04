package com.example.appcsn.features.noiban.domain.repository

import com.example.appcsn.features.noiban.data.LuotDanhGiaNoiBan
import com.example.appcsn.features.noiban.domain.api.DanhGiaNoiBanAPI

class DanhGiaNoiBanRepository(
    private val api: DanhGiaNoiBanAPI
) {
    suspend fun danhGia(luotDanhGia: LuotDanhGiaNoiBan): Result<Boolean> {
        val kq = api.danhGia(luotDanhGia.id_noi_ban, luotDanhGia)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Đánh giá thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }

    suspend fun capNhatDanhGia(luotDanhGia: LuotDanhGiaNoiBan): Result<Boolean> {
        val kq = api.capNhatDanhGia(luotDanhGia.id_noi_ban, luotDanhGia)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Đánh giá thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }

    suspend fun huyDanhGia(idNoiBan: Int, idNguoiDung: String): Result<Boolean> {
        val kq = api.huyDanhGia(idNoiBan, idNguoiDung)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Đánh giá thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }

    suspend fun doc(idNoiBan: Int, idNguoiDung: String): Result<LuotDanhGiaNoiBan> {
        val kq = api.docDanhGia(idNoiBan, idNguoiDung)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Kiểm tra đánh giá thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }

    suspend fun doc(idNoiBan: Int): Result<List<LuotDanhGiaNoiBan>> {
        val kq = api.docDanhGia(idNoiBan)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Kiểm tra đánh giá thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }
}