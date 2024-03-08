package com.example.appcsn.data.remote

import com.example.appcsn.data.model.dacsan.DacSan
import com.example.appcsn.data.model.dacsan.LuotDanhGiaDacSan
import com.example.appcsn.data.model.dacsan.TuKhoaTimKiem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DacSanAPI {
    @GET("/dacsan")
    suspend fun doc(): Response<List<DacSan>>

    @GET("/dacsan/size={size}/index={index}")
    suspend fun doc(
        @Path("size") size: Int,
        @Path("index") index: Int
    ): Response<List<DacSan>>

    @GET("/dacsan/ten={ten}/size={size}/index={index}")
    suspend fun doc(
        @Path("ten") ten: String,
        @Path("size") size: Int,
        @Path("index") index: Int
    ): Response<List<DacSan>>

    @GET("/dacsan/{id}")
    suspend fun doc(@Path("id") id: Int): Response<DacSan>

    @GET("/dacsan/{id}/xem")
    suspend fun xem(
        @Path("id") id: Int,
        @Path("idNguoiDung") idNguoiDung: String
    ): Response<DacSan>

    @POST("/dacsan/ten={ten}/size={size}/index={index}")
    suspend fun timKiem(
        @Path("ten") ten: String,
        @Path("size") size: Int,
        @Path("index") index: Int
    ): Response<List<DacSan>>

    @POST("/dacsan/theovungmien/ten={ten}/size={size}/index={index}")
    suspend fun timKiemTheoVungMien(
        @Path("ten") ten: String,
        @Path("size") size: Int,
        @Path("index") index: Int,
        @Body tuKhoaTimKiem: TuKhoaTimKiem
    ): Response<List<DacSan>>

    @POST("/dacsan/theomua/ten={ten}/size={size}/index={index}")
    suspend fun timKiemTheoMua(
        @Path("ten") ten: String,
        @Path("size") size: Int,
        @Path("index") index: Int,
        @Body tuKhoaTimKiem: TuKhoaTimKiem
    ): Response<List<DacSan>>

    @POST("/dacsan/theovungmien/theomua/ten={ten}/size={size}/index={index}")
    suspend fun timKiemTheoMuaVaVungMien(
        @Path("ten") ten: String,
        @Path("size") size: Int,
        @Path("index") index: Int,
        @Body tuKhoaTimKiem: TuKhoaTimKiem
    ): Response<List<DacSan>>

    @GET("/dacsan/mota={mota}")
    suspend fun docTheoMoTa(@Path("mota") moTa: String): Response<List<DacSan>>

    @GET("/dacsan/cachchebien={cachchebien}")
    suspend fun docTheoCachCheBien(@Path("cachchebien") cachCheBien: String): Response<List<DacSan>>

    @GET("/yeuthich/dacsan={idDacSan}/nguoidung={idNguoiDung}")
    suspend fun checkLike(
        @Path("idDacSan") idDacSan: Int,
        @Path("idNguoiDung") idNguoiDung: String
    ): Response<Boolean>

    @POST("/yeuthich/dacsan={idDacSan}/nguoidung={idNguoiDung}")
    suspend fun like(
        @Path("idDacSan") idDacSan: Int,
        @Path("idNguoiDung") idNguoiDung: String
    ): Response<Boolean>

    @DELETE("/yeuthich/dacsan={idDacSan}/nguoidung={idNguoiDung}")
    suspend fun unlike(
        @Path("idDacSan") idDacSan: Int,
        @Path("idNguoiDung") idNguoiDung: String
    ): Response<Boolean>

    @GET("/danhgia/dacsan={idDacSan}/nguoidung={idNguoiDung}")
    suspend fun docDanhGia(
        @Path("idDacSan") idDacSan: Int,
        @Path("idNguoiDung") idNguoiDung: String
    ): Response<LuotDanhGiaDacSan>

    @POST("/danhgia")
    suspend fun danhGia(@Body luotDanhGiaDacSan: LuotDanhGiaDacSan): Response<Boolean>
}
