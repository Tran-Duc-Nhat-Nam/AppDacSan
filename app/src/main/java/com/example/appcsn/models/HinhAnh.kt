package com.example.appcsn.models

import retrofit2.Response
import retrofit2.http.GET

class HinhAnh(val id: Int, val ten: String, val moTa: String?, val url: String) {
    interface Doc {
        @GET("/hinhanh")
        suspend fun getGenres(): Response<List<HinhAnh>>
    }
}