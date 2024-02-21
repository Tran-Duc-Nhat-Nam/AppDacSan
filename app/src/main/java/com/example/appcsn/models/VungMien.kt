package com.example.appcsn.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import retrofit2.Response
import retrofit2.http.GET

@Parcelize
class VungMien(val id: Int, val ten: String) : Parcelable {
    interface docAPI {
        @GET("/vungmien")
        suspend fun docDanhSach(): Response<List<VungMien>>
    }
}

@Parcelize
data class DanhSachVungMien(val ds: List<VungMien> = mutableListOf()) : Parcelable
