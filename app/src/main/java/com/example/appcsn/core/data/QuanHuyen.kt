package com.example.appcsn.core.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class QuanHuyen(val id: Int, val ten: String, val tinh_thanh: TinhThanh) : Parcelable
