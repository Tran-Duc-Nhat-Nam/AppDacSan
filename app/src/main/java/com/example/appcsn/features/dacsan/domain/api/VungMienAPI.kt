package com.example.appcsn.features.dacsan.domain.api

import com.example.appcsn.features.dacsan.data.VungMien
import retrofit2.Response
import retrofit2.http.GET

interface VungMienAPI {
    @GET("/vungmien")
    suspend fun doc(): Response<List<VungMien>>
}
