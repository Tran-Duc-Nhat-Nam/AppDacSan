package com.example.appcsn.data.remote

import com.example.appcsn.data.model.MuaDacSan
import retrofit2.Response
import retrofit2.http.GET

interface MuaDacSanAPI {
    @GET("/muadacsan")
    suspend fun doc(): Response<List<MuaDacSan>>
}