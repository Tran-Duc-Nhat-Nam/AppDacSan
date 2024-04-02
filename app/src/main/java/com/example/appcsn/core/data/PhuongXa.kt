package com.example.appcsn.core.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class PhuongXa(val id: Int, val ten: String, val quan_huyen: QuanHuyen) : Parcelable
