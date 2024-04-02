package com.example.appcsn.core.domain.api

import com.example.appcsn.core.data.PhuongXa
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PhuongXaAPI {
    @GET("/quanhuyen/{id}/phuongxa")
    suspend fun docDanhSach(@Path("id") id: Int): Response<List<PhuongXa>>
}
