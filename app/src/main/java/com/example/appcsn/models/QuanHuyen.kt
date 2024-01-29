package com.example.appcsn.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

@Parcelize
class QuanHuyen(val id: Int, val ten: String, val tinh_thanh: TinhThanh) : Parcelable {
    interface Doc {
        @GET("/tinhthanh/{id}/quanhuyen")
        suspend fun docDanhSach(@Path("id") id: Int): Response<List<QuanHuyen>>
    }
}