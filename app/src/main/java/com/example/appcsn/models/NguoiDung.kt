package com.example.appcsn.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.Date

@Parcelize
class NguoiDung(
    val id: String,
    val email: String,
    var ten: String,
    var so_dien_thoai: String,
    var is_nam: Boolean,
    var dia_chi: DiaChi,
    var ngay_sinh: Date
) : Parcelable {
    interface API {
        @GET("/nguoidung")
        suspend fun doc(): Response<List<NguoiDung>>

        @GET("/nguoidung/{id}")
        suspend fun doc(@Path("id") id: String): Response<NguoiDung>

        @POST("/nguoidung")
        suspend fun them(@Body nguoiDung: NguoiDung): Response<NguoiDung>
    }
}
