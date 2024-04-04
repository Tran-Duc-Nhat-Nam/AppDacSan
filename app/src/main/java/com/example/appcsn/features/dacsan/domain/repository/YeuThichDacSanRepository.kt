package com.example.appcsn.features.dacsan.domain.repository

import com.example.appcsn.features.dacsan.data.DacSan
import com.example.appcsn.features.dacsan.domain.api.YeuThichDacSanAPI

class YeuThichDacSanRepository(
    private val api: YeuThichDacSanAPI
) {
    suspend fun yeuThich(idDacSan: Int, idNguoiDung: String): Result<Boolean> {
        val kq = api.yeuThich(idDacSan, idNguoiDung)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Yêu thích thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }

    suspend fun huyYeuThich(idDacSan: Int, idNguoiDung: String): Result<Boolean> {
        val kq = api.huyYeuThich(idDacSan, idNguoiDung)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Bỏ yêu thích thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }

    suspend fun kiemTraYeuThich(idDacSan: Int, idNguoiDung: String): Result<Boolean> {
        val kq = api.kiemTraYeuThich(idDacSan, idNguoiDung)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Kiểm tra yêu thích thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }

    suspend fun docDanhSachYeuThich(id: String): Result<List<DacSan>> {
        val kq = api.docDanhSachYeuThich(id)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Đọc dữ liệu thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }

    suspend fun docDanhSachYeuThich(
        idNguoiDung: String,
        pageSize: Int,
        pageIndex: Int
    ): Result<List<DacSan>> {
        val kq = api.docDanhSachYeuThich(idNguoiDung, pageSize, pageIndex)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Kiểm tra đánh giá thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }
}