package com.example.appcsn.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

@Parcelize
class PhuongXa(val id: Int, val ten: String, val quan_huyen: QuanHuyen) : Parcelable {
    interface Doc {
        @GET("/quanhuyen/{id}/phuongxa")
        suspend fun docDanhSach(@Path("id") id: Int): Response<List<PhuongXa>>
    }
}