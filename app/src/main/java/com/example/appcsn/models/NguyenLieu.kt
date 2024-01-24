package com.example.appcsn.models

import retrofit2.Response
import retrofit2.http.GET

class NguyenLieu(val id: Int, val ten: String) {
    interface Doc {
        @GET("/nguyenlieu")
        suspend fun getGenres(): Response<List<NguyenLieu>>
    }
}