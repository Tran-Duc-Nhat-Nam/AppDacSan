package com.example.appcsn.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class QuanHuyen(val id: Int, val ten: String, val tinh_thanh: TinhThanh) : Parcelable
