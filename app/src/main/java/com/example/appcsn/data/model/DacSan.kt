package com.example.appcsn.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DacSan(
    val id: Int,
    val ten: String,
    val mo_ta: String?,
    val cach_che_bien: String?,
    val vung_mien: List<VungMien>,
    val mua_dac_san: List<MuaDacSan>,
    val thanh_phan: List<ThanhPhan>,
    val hinh_dai_dien: HinhAnh,
    val diem_danh_gia: Double = 0.0,
) : Parcelable
