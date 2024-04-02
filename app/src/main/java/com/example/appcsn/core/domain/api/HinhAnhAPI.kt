package com.example.appcsn.core.domain.api

import com.example.appcsn.core.data.HinhAnh
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HinhAnhAPI {
    @GET("/dacsan/{id}/hinhanh")
    suspend fun doc(@Path("id") id: Int): Response<List<HinhAnh>>
}