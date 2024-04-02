package com.example.appcsn.features.dacsan.domain.api

import com.example.appcsn.features.dacsan.data.DacSan
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface YeuThichDacSanAPI {
    @GET("/yeuthich/dacsan/nguoidung={idNguoiDung}")
    suspend fun docDanhSachYeuThich(
        @Path("idNguoiDung") idNguoiDung: String
    ): Response<List<DacSan>>

    @GET("/yeuthich/dacsan={idDacSan}/nguoidung={idNguoiDung}")
    suspend fun kiemTraYeuThich(
        @Path("idDacSan") idDacSan: Int,
        @Path("idNguoiDung") idNguoiDung: String
    ): Response<Boolean>

    @POST("/yeuthich/dacsan={idDacSan}/nguoidung={idNguoiDung}")
    suspend fun yeuThich(
        @Path("idDacSan") idDacSan: Int,
        @Path("idNguoiDung") idNguoiDung: String
    ): Response<Boolean>

    @DELETE("/yeuthich/dacsan={idDacSan}/nguoidung={idNguoiDung}")
    suspend fun huyYeuThich(
        @Path("idDacSan") idDacSan: Int,
        @Path("idNguoiDung") idNguoiDung: String
    ): Response<Boolean>
}