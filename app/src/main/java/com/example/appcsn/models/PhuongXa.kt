package com.example.appcsn.models

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

class PhuongXa(val id: Int, val ten: String, val quan_huyen: QuanHuyen) {
    interface Doc {
        @GET("/quanhuyen/{id}/phuongxa")
        suspend fun getGenres(@Path("id") id: Int): Response<List<PhuongXa>>
    }
}