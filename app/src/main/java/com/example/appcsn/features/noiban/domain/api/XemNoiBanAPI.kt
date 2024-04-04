package com.example.appcsn.features.noiban.domain.api

import com.example.appcsn.features.noiban.data.NoiBan
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface XemNoiBanAPI {
    @GET("/noiban/{id}/chitiet")
    suspend fun xem(
        @Path("id") id: Int,
    ): Response<NoiBan>

    @GET("/noiban/{id}/nguoidung={idNguoiDung}")
    suspend fun xem(
        @Path("id") id: Int,
        @Path("idNguoiDung") idNguoiDung: String
    ): Response<NoiBan>
}