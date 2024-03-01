package com.example.appcsn.data.model.dacsan

import java.time.OffsetDateTime

data class LuotDanhGiaDacSan(
    val id_dac_san: Int = 0,
    val id_nguoi_dung: String = "",
    var thoi_gian_danh_gia: OffsetDateTime = OffsetDateTime.now(),
    var diem_danh_gia: Int = 0,
    var noi_dung: String? = "",
)
