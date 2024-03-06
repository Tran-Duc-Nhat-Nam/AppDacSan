package com.example.appcsn.data.remote

import com.example.appcsn.data.model.NguoiDung
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface NguoiDungAPI {
    @GET("/nguoidung")
    suspend fun doc(): Response<List<NguoiDung>>

    @GET("/nguoidung/{id}")
    suspend fun doc(@Path("id") id: String): Response<NguoiDung>

    @POST("/nguoidung")
    suspend fun them(@Body nguoiDung: NguoiDung): Response<NguoiDung>

    @PUT("/nguoidung")
    suspend fun capNhat(@Body nguoiDung: NguoiDung): Response<Boolean>

    @DELETE("/nguoidung")
    suspend fun xoa(@Path("id") id: String): Response<Boolean>
}
