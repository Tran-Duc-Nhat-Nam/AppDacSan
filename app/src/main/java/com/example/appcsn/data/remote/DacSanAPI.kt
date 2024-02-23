package com.example.appcsn.data.remote

import com.example.appcsn.data.model.DacSan
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.OPTIONS
import retrofit2.http.POST
import retrofit2.http.Path

interface DacSanAPI {
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

    @OPTIONS("/dacsan/{id}/nguoidung={idNguoiDung}")
    suspend fun checkLike(
        @Path("id") id: Int,
        @Path("idNguoiDung") idNguoiDung: String
    ): Response<Int>

    @POST("/dacsan/{id}/nguoidung={idNguoiDung}")
    suspend fun like(
        @Path("id") id: Int,
        @Path("idNguoiDung") idNguoiDung: String
    ): Response<Boolean>
}
