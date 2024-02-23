package com.example.appcsn.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class NoiBan(
    val id: Int,
    val ten: String,
    val mo_ta: String?,
    val dia_chi: DiaChi,
) : Parcelable
