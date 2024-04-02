package com.example.appcsn.features.dacsan.domain.api

import com.example.appcsn.features.dacsan.data.NguyenLieu
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NguyenLieuAPI {
    @GET("/nguyenlieu")
    suspend fun doc(): Response<List<NguyenLieu>>

    @GET("/nguyenlieu/ten={ten}/size={size}/index={index}")
    suspend fun doc(
        @Path("ten") ten: String,
        @Path("size") size: Int,
        @Path("index") index: Int
    ): Response<List<NguyenLieu>>
}