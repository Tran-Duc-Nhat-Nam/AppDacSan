package com.example.appcsn.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

@Parcelize
class TinhThanh(val id: Int, val ten: String) : Parcelable {
    interface docAPI {
        @GET("/tinhthanh")
        suspend fun docDanhSach(): Response<List<TinhThanh>>

        @GET("/tinhthanh/{id}")
        suspend fun docTheoID(@Path("id") id: Int): Response<TinhThanh>
    }
}