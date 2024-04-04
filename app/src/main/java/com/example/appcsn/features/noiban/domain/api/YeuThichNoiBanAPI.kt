package com.example.appcsn.features.noiban.domain.api

import com.example.appcsn.features.noiban.data.NoiBan
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface YeuThichNoiBanAPI {
    @GET("/yeuthich/noiban/nguoidung={idNguoiDung}")
    suspend fun docDanhSachYeuThich(
        @Path("idNguoiDung") idNguoiDung: String
    ): Response<List<NoiBan>>

    @GET("/yeuthich/noiban={id}/nguoidung={idNguoiDung}")
    suspend fun kiemTraYeuThich(
        @Path("id") id: Int,
        @Path("idNguoiDung") idNguoiDung: String
    ): Response<Boolean>

    @POST("/yeuthich/noiban={id}/nguoidung={idNguoiDung}")
    suspend fun yeuThich(
        @Path("id") id: Int,
        @Path("idNguoiDung") idNguoiDung: String
    ): Response<Boolean>

    @DELETE("/yeuthich/noiban={id}/nguoidung={idNguoiDung}")
    suspend fun huyYeuThich(
        @Path("id") id: Int,
        @Path("idNguoiDung") idNguoiDung: String
    ): Response<Boolean>
}