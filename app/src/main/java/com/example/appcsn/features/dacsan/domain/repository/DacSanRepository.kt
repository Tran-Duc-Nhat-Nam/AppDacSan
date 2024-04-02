package com.example.appcsn.features.dacsan.domain.repository

import com.example.appcsn.features.dacsan.data.DacSan
import com.example.appcsn.features.dacsan.data.TuKhoaTimKiem
import com.example.appcsn.features.dacsan.domain.api.DacSanAPI
import retrofit2.Response

class DacSanRepository(
    private val api: DacSanAPI
) {
    suspend fun doc(): Result<List<DacSan>> {
        val kq = api.timKiem()
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Đọc dữ liệu thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }

    suspend fun doc(id: Int): Result<DacSan> {
        val kq = api.timKiem(id)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Đọc dữ liệu thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }


    suspend fun timKiem(
        ten: String,
        tuKhoa: TuKhoaTimKiem,
        pageSize: Int,
        pageIndex: Int
    ): Result<List<DacSan>> {
        val coTen = ten.isNotBlank()
        val coVungMien = tuKhoa.dsVungMien.isNotEmpty()
        val coMua = tuKhoa.dsMuaDacSan.isNotEmpty()
        val coNguyenLieu = tuKhoa.dsNguyenLieu.isNotEmpty()

        val kq: Response<List<DacSan>> = when {
            coVungMien && coMua && coNguyenLieu -> {
                api.timKiemTheoDieuKien(ten, pageSize, pageIndex, tuKhoa)
            }

            coVungMien && coMua -> {
                api.timKiemTheoMuaVungMien(ten, pageSize, pageIndex, tuKhoa)
            }

            coVungMien && coNguyenLieu -> {
                api.timKiemTheoNguyenLieuVungMien(ten, pageSize, pageIndex, tuKhoa)
            }

            coMua && coNguyenLieu -> {
                api.timKiemTheoNguyenLieuMua(ten, pageSize, pageIndex, tuKhoa)
            }

            coVungMien -> {
                api.timKiemTheoVungMien(ten, pageSize, pageIndex, tuKhoa)
            }

            coMua -> {
                api.timKiemTheoMua(ten, pageSize, pageIndex, tuKhoa)
            }

            coNguyenLieu -> {
                api.timKiemTheoNguyenLieu(ten, pageSize, pageIndex, tuKhoa)
            }

            coTen -> {
                api.timKiem(ten, pageSize, pageIndex)
            }

            else -> {
                return Result.failure(Throwable(message = "Từ khóa tìm kiếm không hợp lệ"))
            }
        }

        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Đọc dữ liệu thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }
}
