package com.example.appcsn.data.model.noiban

import android.os.Parcelable
import com.example.appcsn.data.model.DiaChi
import kotlinx.parcelize.Parcelize

@Parcelize
class NoiBan(
    val id: Int,
    val ten: String,
    val mo_ta: String?,
    val dia_chi: DiaChi,
    val diem_danh_gia: Double = 0.0,
    val luot_danh_gia: Int = 0,
    val luot_xem: Int = 0,
) : Parcelable
