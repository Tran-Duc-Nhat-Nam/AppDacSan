package com.example.appcsn.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

@Parcelize
class DacSan(
    val id: Int,
    val ten: String,
    val mo_ta: String?,
    val cach_che_bien: String?,
    val vung_mien: List<VungMien>,
    val mua_dac_san: List<MuaDacSan>,
    val thanh_phan: List<ThanhPhan>,
    val hinh_dai_dien: HinhAnh,
    val diem_danh_gia: Double = 0.0,
) : Parcelable {
    interface doc {
        @GET("/dacsan")
        suspend fun doc(): Response<List<DacSan>>

        @GET("/dacsan/size={size}/index={index}")
        suspend fun docTheoTrang(
            @Path("size") size: Int,
            @Path("index") index: Int
        ): Response<List<DacSan>>

        @GET("/dacsan/{id}")
        suspend fun docTheoID(@Path("id") id: Int): Response<DacSan>

        @GET("/dacsan/{id}/nguoidung={idNguoiDung}")
        suspend fun xem(
            @Path("id") id: Int,
            @Path("idNguoiDung") idNguoiDung: String
        ): Response<DacSan>

        @GET("/dacsan/ten={ten}")
        suspend fun docTheoTen(@Path("ten") ten: String): Response<List<DacSan>>

        @GET("/dacsan/mota={mota}")
        suspend fun docTheoMoTa(@Path("mota") moTa: String): Response<List<DacSan>>

        @GET("/dacsan/cachchebien={cachchebien}")
        suspend fun docTheoCachCheBien(@Path("cachchebien") cachCheBien: String): Response<List<DacSan>>

        @POST("/dacsan/{id}/nguoidung={idNguoiDung}")
        suspend fun like(
            @Path("id") id: Int,
            @Path("idNguoiDung") idNguoiDung: String
        ): Response<String>
    }
}
