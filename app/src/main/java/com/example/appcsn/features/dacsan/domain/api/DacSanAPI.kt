package com.example.appcsn.features.dacsan.domain.api

import com.example.appcsn.features.dacsan.data.DacSan
import com.example.appcsn.features.dacsan.data.TuKhoaTimKiem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DacSanAPI {
    @GET("/dacsan")
    suspend fun timKiem(): Response<List<DacSan>>

    @GET("/dacsan/size={size}/index={index}")
    suspend fun timKiem(
        @Path("size") size: Int,
        @Path("index") index: Int
    ): Response<List<DacSan>>

    @GET("/dacsan/{id}")
    suspend fun timKiem(@Path("id") id: Int): Response<DacSan>

    @GET("/dacsan/ten={ten}/size={size}/index={index}")
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
    suspend fun timKiemTheoMuaVungMien(
        @Path("ten") ten: String,
        @Path("size") size: Int,
        @Path("index") index: Int,
        @Body tuKhoaTimKiem: TuKhoaTimKiem
    ): Response<List<DacSan>>

    @POST("/dacsan/theonguyenlieu/ten={ten}/size={size}/index={index}")
    suspend fun timKiemTheoNguyenLieu(
        @Path("ten") ten: String,
        @Path("size") size: Int,
        @Path("index") index: Int,
        @Body tuKhoaTimKiem: TuKhoaTimKiem
    ): Response<List<DacSan>>

    @POST("/dacsan/theonguyenlieu/theovungmien/ten={ten}/size={size}/index={index}")
    suspend fun timKiemTheoNguyenLieuVungMien(
        @Path("ten") ten: String,
        @Path("size") size: Int,
        @Path("index") index: Int,
        @Body tuKhoaTimKiem: TuKhoaTimKiem
    ): Response<List<DacSan>>

    @POST("/dacsan/theonguyenlieu/theomua/ten={ten}/size={size}/index={index}")
    suspend fun timKiemTheoNguyenLieuMua(
        @Path("ten") ten: String,
        @Path("size") size: Int,
        @Path("index") index: Int,
        @Body tuKhoaTimKiem: TuKhoaTimKiem
    ): Response<List<DacSan>>

    @POST("/dacsan/theodieukien/ten={ten}/size={size}/index={index}")
    suspend fun timKiemTheoDieuKien(
        @Path("ten") ten: String,
        @Path("size") size: Int,
        @Path("index") index: Int,
        @Body tuKhoaTimKiem: TuKhoaTimKiem
    ): Response<List<DacSan>>


}
