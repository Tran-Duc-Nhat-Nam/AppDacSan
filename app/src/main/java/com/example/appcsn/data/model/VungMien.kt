package com.example.appcsn.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VungMien(val id: Int, val ten: String) : Parcelable

@Parcelize
data class DanhSachVungMien(val ds: List<VungMien> = mutableListOf()) : Parcelable
