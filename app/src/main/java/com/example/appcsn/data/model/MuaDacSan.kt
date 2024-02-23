package com.example.appcsn.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import retrofit2.Response
import retrofit2.http.GET

@Parcelize
class MuaDacSan(val id: Int, val ten: String) : Parcelable {
    interface Doc {
        @GET("/muadacsan")
        suspend fun docDanhSach(): Response<List<MuaDacSan>>
    }
}

@Parcelize
data class DanhSachMuaDacSan(val ds: List<MuaDacSan> = mutableListOf()) : Parcelable
