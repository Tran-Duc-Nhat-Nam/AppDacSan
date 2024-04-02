package com.example.appcsn.features.dacsan.domain.api

import com.example.appcsn.features.dacsan.data.MuaDacSan
import retrofit2.Response
import retrofit2.http.GET

interface MuaDacSanAPI {
    @GET("/muadacsan")
    suspend fun doc(): Response<List<MuaDacSan>>
}