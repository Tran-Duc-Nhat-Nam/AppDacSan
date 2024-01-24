package com.example.appcsn.models

import retrofit2.Response
import retrofit2.http.GET

class TinhThanh(val id: Int, val ten: String) {
    interface Doc {
        @GET("/tinhthanh")
        suspend fun getGenres(): Response<List<TinhThanh>>
    }
}