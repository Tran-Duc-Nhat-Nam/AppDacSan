package com.example.appcsn.features.noiban.domain.api

import com.example.appcsn.features.noiban.data.LuotDanhGiaNoiBan
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface DanhGiaNoiBanAPI {
    @GET("/danhgia/noiban={idDacSan}")
    suspend fun docDanhGia(
        @Path("idDacSan") idDacSan: Int,
    ): Response<List<LuotDanhGiaNoiBan>>

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

    @PUT("/danhgia/noiban={id}")
    suspend fun capNhatDanhGia(
        @Path("id") id: Int,
        @Body luotDanhGia: LuotDanhGiaNoiBan
    ): Response<Boolean>

    @DELETE("/danhgia/noiban={idDacSan}/nguoidung={idNguoiDung}")
    suspend fun huyDanhGia(
        @Path("idDacSan") idDacSan: Int,
        @Path("idNguoiDung") idNguoiDung: String,
    ): Response<Boolean>
}