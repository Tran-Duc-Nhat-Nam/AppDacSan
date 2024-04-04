package com.example.appcsn.features.noiban.domain.api

import com.example.appcsn.features.dacsan.data.DacSan
import com.example.appcsn.features.noiban.data.NoiBan
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NoiBanAPI {
    @GET("/noiban")
    suspend fun doc(): Response<List<NoiBan>>

    @GET("/noiban/{id}")
    suspend fun doc(@Path("id") id: Int): Response<NoiBan>

    @GET("/noiban/{id}/dacsan")
    suspend fun docDacSan(@Path("id") id: Int): Response<List<DacSan>>

    @GET("/noiban/ten={ten}/size={size}/index={index}")
    suspend fun timKiem(
        @Path("ten") ten: String,
        @Path("size") size: Int,
        @Path("index") index: Int
    ): Response<List<NoiBan>>
}
