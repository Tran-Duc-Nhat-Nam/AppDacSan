package com.example.appcsn.data.remote

import com.example.appcsn.data.model.DacSan
import com.example.appcsn.data.model.NoiBan
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NoiBanAPI {
    @GET("/noiban")
    suspend fun docDanhSach(): Response<List<NoiBan>>

    @GET("/noiban/{id}")
    suspend fun docTheoTen(@Path("id") id: Int): Response<NoiBan>

    @GET("/noiban/{id}/dacsan")
    suspend fun docDanhSachDacSan(@Path("id") id: Int): Response<List<DacSan>>
}
