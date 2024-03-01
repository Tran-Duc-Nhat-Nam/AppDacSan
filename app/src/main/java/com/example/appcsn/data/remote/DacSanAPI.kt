package com.example.appcsn.data.remote

import com.example.appcsn.data.model.dacsan.DacSan
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
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

    @GET("/dacsan/ten={ten}/size={size}/index={index}")
    suspend fun docTrangTheoTen(
        @Path("ten") ten: String,
        @Path("size") size: Int,
        @Path("index") index: Int
    ): Response<List<DacSan>>

    @GET("/dacsan/{id}")
    suspend fun docTheoID(@Path("id") id: Int): Response<DacSan>

    @GET("/dacsan/{id}/xem")
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
}
