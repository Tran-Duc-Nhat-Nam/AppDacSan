package com.example.appcsn.features.dacsan.domain.repository

import com.example.appcsn.features.dacsan.data.LuotDanhGiaDacSan
import com.example.appcsn.features.dacsan.domain.api.DanhGiaDacSanAPI

class DanhGiaDacSanRepository(
    private val api: DanhGiaDacSanAPI
) {
    suspend fun danhGia(luotDanhGiaDacSan: LuotDanhGiaDacSan): Result<Boolean> {
        val kq = api.danhGia(luotDanhGiaDacSan.id_dac_san, luotDanhGiaDacSan)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Đánh giá thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }

    suspend fun capNhatDanhGia(luotDanhGiaDacSan: LuotDanhGiaDacSan): Result<Boolean> {
        val kq = api.capNhatDanhGia(luotDanhGiaDacSan.id_dac_san, luotDanhGiaDacSan)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Đánh giá thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }

    suspend fun huyDanhGia(idDacSan: Int, idNguoiDung: String): Result<Boolean> {
        val kq = api.huyDanhGia(idDacSan, idNguoiDung)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Đánh giá thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }

    suspend fun doc(idDacSan: Int, idNguoiDung: String): Result<LuotDanhGiaDacSan> {
        val kq = api.docDanhGia(idDacSan, idNguoiDung)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Kiểm tra đánh giá thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }

    suspend fun doc(idDacSan: Int): Result<List<LuotDanhGiaDacSan>> {
        val kq = api.docDanhGia(idDacSan)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Kiểm tra đánh giá thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }
}