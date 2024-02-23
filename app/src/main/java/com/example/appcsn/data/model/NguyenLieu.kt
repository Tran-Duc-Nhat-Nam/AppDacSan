package com.example.appcsn.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import retrofit2.Response
import retrofit2.http.GET

@Parcelize
class NguyenLieu(val id: Int, val ten: String) : Parcelable {
    interface Doc {
        @GET("/nguyenlieu")
        suspend fun docDanhSach(): Response<List<NguyenLieu>>
    }
}

@Parcelize
data class DanhSachNguyenLieu(val ds: List<NguyenLieu> = mutableListOf()) : Parcelable
