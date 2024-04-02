package com.example.appcsn.features.dacsan.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TuKhoaTimKiem(
    val dsVungMien: MutableList<Int> = mutableListOf(),
    val dsMuaDacSan: MutableList<Int> = mutableListOf(),
    val dsNguyenLieu: MutableList<Int> = mutableListOf()
) : Parcelable