package com.example.appcsn.features.nguoidung.domain.repository

import com.example.appcsn.features.nguoidung.data.NguoiDung
import com.example.appcsn.features.nguoidung.domain.api.NguoiDungAPI

class NguoiDungRepository(
    private val api: NguoiDungAPI
) {
    suspend fun doc(): Result<List<NguoiDung>> {
        val kq = api.doc()
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Đọc dữ liệu thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }

    suspend fun doc(id: String): Result<NguoiDung> {
        val kq = api.doc(id)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Đọc dữ liệu thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }

    suspend fun timKiem(ten: String, pageSize: Int, pageIndex: Int): Result<List<NguoiDung>> {
        val kq = api.timKiem(ten, pageSize, pageIndex)
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
