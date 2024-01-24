package com.example.appcsn.models

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

class DacSan(
    val id: Int,
    val ten: String,
    val mo_ta: String?,
    val cach_che_bien: String?,
    val vung_mien: List<VungMien>,
    val mua_dac_san: List<MuaDacSan>,
    val thanh_phan: List<ThanhPhan>,
    val hinh_dai_dien: HinhAnh,
) {
    interface doc {
        @GET("/dacsan")
        suspend fun doc(): Response<List<DacSan>>
    }

    interface docTheoID {
        @GET("/dacsan/{id}")
        suspend fun doc(@Path("id") id: Int): Response<DacSan>
    }

    interface docTheoTen {
        @GET("/dacsan/ten={ten}")
        suspend fun doc(@Path("ten") ten: String): Response<List<DacSan>>
    }

    interface docTheoMoTa {
        @GET("/dacsan/mota={mota}")
        suspend fun doc(@Path("mota") moTa: String): Response<List<DacSan>>
    }

    interface docTheoCachCheBien {
        @GET("/dacsan/cachchebien={cachchebien}")
        suspend fun doc(@Path("cachchebien") cachCheBien: String): Response<List<DacSan>>
    }
}