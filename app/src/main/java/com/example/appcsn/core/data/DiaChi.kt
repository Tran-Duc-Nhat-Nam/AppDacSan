package com.example.appcsn.core.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DiaChi(val id: Int, val so_nha: String, val ten_duong: String, val phuong_xa: PhuongXa) :
    Parcelable {
    override fun toString(): String {
        return "$so_nha $ten_duong, ${phuong_xa.ten}, ${phuong_xa.quan_huyen.ten}, ${phuong_xa.quan_huyen.tinh_thanh.ten}"
    }
}
