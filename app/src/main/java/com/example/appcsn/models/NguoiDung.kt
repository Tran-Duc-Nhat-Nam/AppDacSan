package com.example.appcsn.models

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.Date

class NguoiDung(
    val id: Int,
    val email: String,
    var ten: String,
    var so_dien_thoai: String,
    var is_nam: Boolean,
    var dia_chi: DiaChi,
    var ngay_sinh: Date
) {
    interface doc {
        @GET("/nguoidung")
        suspend fun doc(): Response<List<NguoiDung>>
    }

    interface docTheoID {
        @GET("/nguoidung/{id}")
        suspend fun doc(@Path("id") id: Int): Response<NguoiDung>
    }
}