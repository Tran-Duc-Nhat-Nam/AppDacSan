package com.example.appcsn.data.remote

import com.example.appcsn.data.model.TinhThanh
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TinhThanhAPI {
    @GET("/tinhthanh")
    suspend fun docDanhSach(): Response<List<TinhThanh>>

    @GET("/tinhthanh/{id}")
    suspend fun docTheoID(@Path("id") id: Int): Response<TinhThanh>
}
