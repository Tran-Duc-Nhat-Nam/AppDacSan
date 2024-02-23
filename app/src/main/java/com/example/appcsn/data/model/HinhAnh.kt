package com.example.appcsn.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import retrofit2.Response
import retrofit2.http.GET

@Parcelize
class HinhAnh(val id: Int, val ten: String, val moTa: String?, val url: String) : Parcelable {
    interface Doc {
        @GET("/hinhanh")
        suspend fun docDanhSach(): Response<List<HinhAnh>>
    }
}
