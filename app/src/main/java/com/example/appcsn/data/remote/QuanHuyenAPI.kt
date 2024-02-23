package com.example.appcsn.data.remote

import com.example.appcsn.data.model.QuanHuyen
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface QuanHuyenAPI {
    @GET("/tinhthanh/{id}/quanhuyen")
    suspend fun docDanhSach(@Path("id") id: Int): Response<List<QuanHuyen>>
}
