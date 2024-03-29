package com.example.appcsn.data.remote

import com.example.appcsn.data.model.dacsan.DacSan
import com.example.appcsn.data.model.noiban.LuotDanhGiaNoiBan
import com.example.appcsn.data.model.noiban.NoiBan
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NoiBanAPI {
    @GET("/noiban")
    suspend fun doc(): Response<List<NoiBan>>

    @GET("/noiban/{id}")
    suspend fun doc(@Path("id") id: Int): Response<NoiBan>

    @GET("/noiban/{id}/dacsan")
    suspend fun docDacSan(@Path("id") id: Int): Response<List<DacSan>>

    @GET("/noiban/{id}/chitiet")
    suspend fun xem(
        @Path("id") id: Int,
    ): Response<NoiBan>

    @GET("/noiban/{id}/nguoidung={idNguoiDung}")
    suspend fun xem(
        @Path("id") id: Int,
        @Path("idNguoiDung") idNguoiDung: String
    ): Response<NoiBan>

    @GET("/noiban/ten={ten}/size={size}/index={index}")
    suspend fun timKiem(
        @Path("ten") ten: String,
        @Path("size") size: Int,
        @Path("index") index: Int
    ): Response<List<NoiBan>>

    @GET("/yeuthich/noiban={id}/nguoidung={idNguoiDung}")
    suspend fun checkLike(
        @Path("id") id: Int,
        @Path("idNguoiDung") idNguoiDung: String
    ): Response<Boolean>

    @POST("/yeuthich/noiban={id}/nguoidung={idNguoiDung}")
    suspend fun like(
        @Path("id") id: Int,
        @Path("idNguoiDung") idNguoiDung: String
    ): Response<Boolean>

    @DELETE("/yeuthich/noiban={id}/nguoidung={idNguoiDung}")
    suspend fun unlike(
        @Path("id") id: Int,
        @Path("idNguoiDung") idNguoiDung: String
    ): Response<Boolean>

    @GET("/danhgia/noiban={id}/nguoidung={idNguoiDung}")
    suspend fun docDanhGia(
        @Path("id") id: Int,
        @Path("idNguoiDung") idNguoiDung: String
    ): Response<LuotDanhGiaNoiBan>

    @POST("/danhgia/noiban={id}")
    suspend fun danhGia(
        @Path("id") id: Int,
        @Body luotDanhGia: LuotDanhGiaNoiBan
    ): Response<Boolean>
}
