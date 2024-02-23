package com.example.appcsn.data.remote

import com.example.appcsn.data.model.NguoiDung
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NguoiDungAPI {
    @GET("/nguoidung")
    suspend fun docDanhSach(): Response<List<NguoiDung>>

    @GET("/nguoidung/{id}")
    suspend fun docTheoID(@Path("id") id: String): Response<NguoiDung>

    @POST("/nguoidung")
    suspend fun them(@Body nguoiDung: NguoiDung): Response<NguoiDung>
}
