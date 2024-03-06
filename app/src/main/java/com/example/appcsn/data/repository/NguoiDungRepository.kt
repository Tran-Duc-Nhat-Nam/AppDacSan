package com.example.appcsn.data.repository

import com.example.appcsn.data.model.NguoiDung
import com.example.appcsn.data.remote.NguoiDungAPI

class NguoiDungRepository(
    private val api: NguoiDungAPI
) {
    suspend fun docDanhSach(): Result<List<NguoiDung>> {
        val kq = api.doc()
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Đọc dữ liệu thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }

    suspend fun docTheoID(id: String): Result<NguoiDung> {
        val kq = api.doc(id)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Đọc dữ liệu thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }

    suspend fun them(nguoiDung: NguoiDung): Result<NguoiDung> {
        val kq = api.them(nguoiDung)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Đọc dữ liệu thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }

    suspend fun capNhat(nguoiDung: NguoiDung): Result<Boolean> {
        val kq = api.capNhat(nguoiDung)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Sửa dữ liệu thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }

    suspend fun xoa(id: String): Result<Boolean> {
        val kq = api.xoa(id)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Đọc dữ liệu thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }
}
