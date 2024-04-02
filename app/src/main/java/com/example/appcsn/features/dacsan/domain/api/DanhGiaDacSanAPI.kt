package com.example.appcsn.features.dacsan.domain.api

import com.example.appcsn.features.dacsan.data.LuotDanhGiaDacSan
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DanhGiaDacSanAPI {
    @GET("/danhgia/dacsan={idDacSan}")
    suspend fun docDanhGia(
        @Path("idDacSan") idDacSan: Int,
    ): Response<List<LuotDanhGiaDacSan>>

    @GET("/danhgia/dacsan={idDacSan}/nguoidung={idNguoiDung}")
    suspend fun docDanhGia(
        @Path("idDacSan") idDacSan: Int,
        @Path("idNguoiDung") idNguoiDung: String
    ): Response<LuotDanhGiaDacSan>

    @POST("/danhgia/dacsan={id}")
    suspend fun danhGia(
        @Path("id") id: Int,
        @Body luotDanhGia: LuotDanhGiaDacSan
    ): Response<Boolean>
}