package com.example.appcsn.features.dacsan.domain.api

import com.example.appcsn.features.dacsan.data.DacSan
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface XemDacSanAPI {
    @GET("/dacsan/{id}/chitiet")
    suspend fun xem(
        @Path("id") id: Int,
    ): Response<DacSan>

    @GET("/dacsan/{id}/nguoidung={idNguoiDung}")
    suspend fun xem(
        @Path("id") id: Int,
        @Path("idNguoiDung") idNguoiDung: String
    ): Response<DacSan>

    @GET("/lichsu/dacsan/nguoidung={id}")
    suspend fun docLichSuXem(
        @Path("id") id: String
    ): Response<List<DacSan>>
}