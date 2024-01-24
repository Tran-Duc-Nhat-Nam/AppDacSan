package com.example.appcsn.models

import retrofit2.Response
import retrofit2.http.GET

class VungMien(val id: Int, val ten: String) {
    interface Doc {
        @GET("/vungmien")
        suspend fun getGenres(): Response<List<VungMien>>
    }
}