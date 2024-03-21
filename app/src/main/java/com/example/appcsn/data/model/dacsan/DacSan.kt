package com.example.appcsn.data.model.dacsan

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.appcsn.data.model.HinhAnh
import com.example.appcsn.data.model.MuaDacSan
import com.example.appcsn.data.model.ThanhPhan
import com.example.appcsn.data.model.VungMien
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "dac_san")
data class DacSan(
    @PrimaryKey val id: Int,
    val ten: String,
    val mo_ta: String?,
    val cach_che_bien: String?,
    val vung_mien: List<VungMien>,
    val mua_dac_san: List<MuaDacSan>,
    val thanh_phan: List<ThanhPhan>,
    val hinh_dai_dien: HinhAnh,
    val diem_danh_gia: Double = 0.0,
    val luot_danh_gia: Int = 0,
    val luot_xem: Int = 0,
) : Parcelable
