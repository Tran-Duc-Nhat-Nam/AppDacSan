package com.example.appcsn.models

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

class QuanHuyen(val id: Int, val ten: String, val tinh_thanh: TinhThanh) {
    interface Doc {
        @GET("/tinhthanh/{id}/quanhuyen")
        suspend fun getGenres(@Path("id") id: Int): Response<List<QuanHuyen>>
    }
}