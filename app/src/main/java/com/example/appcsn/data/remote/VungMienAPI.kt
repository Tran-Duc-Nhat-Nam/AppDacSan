package com.example.appcsn.data.remote

import com.example.appcsn.data.model.VungMien
import retrofit2.Response
import retrofit2.http.GET

interface VungMienAPI {
    @GET("/vungmien")
    suspend fun docDanhSach(): Response<List<VungMien>>
}
