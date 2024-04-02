package com.example.appcsn.core.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HinhAnh(val id: Int, val ten: String, val moTa: String?, val url: String) : Parcelable
