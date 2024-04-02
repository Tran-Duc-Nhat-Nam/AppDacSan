package com.example.appcsn.core.domain.api

import com.example.appcsn.core.data.TinhThanh
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TinhThanhAPI {
    @GET("/tinhthanh")
    suspend fun docDanhSach(): Response<List<TinhThanh>>

    @GET("/tinhthanh/{id}")
    suspend fun docTheoID(@Path("id") id: Int): Response<TinhThanh>
}
