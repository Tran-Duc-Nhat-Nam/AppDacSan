package com.example.appcsn.features.nguoidung.data

import android.os.Parcelable
import com.example.appcsn.core.data.DiaChi
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class NguoiDung(
    val id: String,
    val email: String,
    var ten: String,
    var so_dien_thoai: String,
    var is_nam: Boolean,
    var dia_chi: DiaChi,
    var ngay_sinh: Date
) : Parcelable
