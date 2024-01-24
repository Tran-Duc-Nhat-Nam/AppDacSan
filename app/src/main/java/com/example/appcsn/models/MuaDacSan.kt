package com.example.appcsn.models

import retrofit2.Response
import retrofit2.http.GET

class MuaDacSan(val id: Int, val ten: String) {
    interface Doc {
        @GET("/muadacsan")
        suspend fun getGenres(): Response<List<MuaDacSan>>
    }
}