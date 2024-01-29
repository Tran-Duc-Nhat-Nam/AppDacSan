package com.example.appcsn.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

@Parcelize
class NoiBan(
    val id: Int,
    val ten: String,
    val mo_ta: String?,
    val dia_chi: DiaChi,
) : Parcelable {
    interface doc {
        @GET("/noiban")
        suspend fun doc(): Response<List<NoiBan>>
    }

    interface docTheoID {
        @GET("/noiban/{id}")
        suspend fun doc(@Path("id") id: Int): Response<NoiBan>
    }

    interface docDacSan {
        @GET("/noiban/{id}/dacsan")
        suspend fun doc(@Path("id") id: Int): Response<List<DacSan>>
    }
}