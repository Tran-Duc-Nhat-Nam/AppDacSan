package com.example.appcsn.data.repository

import com.example.appcsn.data.model.dacsan.DacSan
import com.example.appcsn.data.model.dacsan.LuotDanhGiaDacSan
import com.example.appcsn.data.model.dacsan.TuKhoaTimKiem
import com.example.appcsn.data.remote.DacSanAPI
import retrofit2.Response

class DacSanRepository(
    private val api: DacSanAPI
) {
    suspend fun doc(): Result<List<DacSan>> {
        val kq = api.doc()
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
        val kq: Response<List<DacSan>> =
            if (tuKhoa.dsVungMien.isNotEmpty() && tuKhoa.dsMuaDacSan.isNotEmpty()) {
                api.timKiemTheoMuaVaVungMien(ten, pageSize, pageIndex, tuKhoa)
            } else if (tuKhoa.dsVungMien.isNotEmpty()) {
                api.timKiemTheoVungMien(ten, pageSize, pageIndex, tuKhoa)
            } else if (tuKhoa.dsMuaDacSan.isNotEmpty()) {
                api.timKiemTheoMua(ten, pageSize, pageIndex, tuKhoa)
            } else if (ten.isNotEmpty()) {
                api.doc(ten, pageSize, pageIndex)
            } else {
                return Result.failure(Throwable(message = "Từ khóa tìm kiếm không hợp lệ"))
            }

        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Đọc dữ liệu thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }

    suspend fun docTheoTrang(pageSize: Int, pageIndex: Int): Result<List<DacSan>> {
        val kq = api.doc(pageSize, pageIndex)
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

    suspend fun rate(luotDanhGiaDacSan: LuotDanhGiaDacSan): Result<Boolean> {
        val kq = api.danhGia(luotDanhGiaDacSan)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Đánh giá thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }

    suspend fun checkRate(idDacSan: Int, idNguoiDung: String): Result<LuotDanhGiaDacSan> {
        val kq = api.docDanhGia(idDacSan, idNguoiDung)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Kiểm tra đánh giá thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }
}
