package com.example.appcsn.data.model.dacsan

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.Instant
import java.util.Date

@Parcelize
data class LuotDanhGiaDacSan(
    val id_dac_san: Int = 0,
    val id_nguoi_dung: String = "",
    var thoi_gian_danh_gia: Date = Date.from(Instant.now()),
    var diem_danh_gia: Int = 0,
    var noi_dung: String? = "",
) : Parcelable
