package com.example.appcsn.features.dacsan.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ThanhPhan(val nguyen_lieu: NguyenLieu, val so_luong: Int, val don_vi_tinh: String) :
    Parcelable
