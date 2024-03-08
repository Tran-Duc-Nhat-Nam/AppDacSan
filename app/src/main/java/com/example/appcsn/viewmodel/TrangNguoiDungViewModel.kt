package com.example.appcsn.viewmodel

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.appcsn.data.model.DiaChi
import com.example.appcsn.data.model.NguoiDung
import com.example.appcsn.data.model.PhuongXa
import com.example.appcsn.data.model.QuanHuyen
import com.example.appcsn.data.model.TinhThanh
import com.example.appcsn.data.repository.NguoiDungRepository
import com.example.appcsn.data.repository.PhuongXaRepository
import com.example.appcsn.data.repository.QuanHuyenRepository
import com.example.appcsn.data.repository.TinhThanhRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TrangNguoiDungViewModel @Inject constructor(
    private val tinhThanhRepository: TinhThanhRepository,
    private val quanHuyenRepository: QuanHuyenRepository,
    private val phuongXaRepository: PhuongXaRepository,
    private val nguoiDungRepository: NguoiDungRepository
) : BaseViewModel() {
    val auth = Firebase.auth
    var email =
        mutableStateOf("")
    var matKhau =
        mutableStateOf("")
    var xacNhanMatKhau =
        mutableStateOf("")
    var ten =
        mutableStateOf("")
    var sdt =
        mutableStateOf("")
    var isNam =
        mutableStateOf(true)
    var date =
        mutableStateOf(LocalDate.now())
    var tenDuong =
        mutableStateOf("")
    var soNha =
        mutableStateOf("")
    var tinhThanh =
        mutableStateOf<TinhThanh?>(null)
    var quanHuyen =
        mutableStateOf<QuanHuyen?>(null)
    var phuongXa =
        mutableStateOf<PhuongXa?>(null)
    var dsTinhThanh = listOf<TinhThanh>()
    var dsQuanHuyen = listOf<QuanHuyen>()
    var dsPhuongXa = listOf<PhuongXa>()

    init {
        viewModelScope.launch {
            loading.value = true
            val job = viewModelScope.launch {
                docTinhThanh()
            }
            job.join()
            loading.value = false
        }
    }

    fun clear() {
        email.value = ""
        matKhau.value = ""
        xacNhanMatKhau.value = ""
        ten.value = ""
        sdt.value = ""
        isNam.value = true
        date.value = LocalDate.now()
        tenDuong.value = ""
        soNha.value = ""
        tinhThanh.value = null
    }

    suspend fun docNguoiDungFirebase() {
        loading.value = true
        val user = Firebase.auth.currentUser
        if (user != null) {
            val job = viewModelScope.launch {
                nguoiDung = nguoiDungRepository.docTheoID(user.uid).getOrNull()
            }
            job.join()
            ten.value = nguoiDung!!.ten
            sdt.value = nguoiDung!!.so_dien_thoai
            date.value = nguoiDung!!.ngay_sinh.toInstant()
                .atZone(
                    ZoneId.systemDefault()
                ).toLocalDate()
            soNha.value = nguoiDung!!.dia_chi.so_nha
            tenDuong.value = nguoiDung!!.dia_chi.ten_duong
            phuongXa.value = nguoiDung!!.dia_chi.phuong_xa
        }
        loading.value = false
    }

    private suspend fun docTinhThanh() {
        val kq = tinhThanhRepository.docDanhSach()

        if (kq.getOrNull() != null) {
            dsTinhThanh = kq.getOrNull() ?: emptyList()
        }
    }

    fun docQuanHuyen() {
        viewModelScope.launch {
            val kq = quanHuyenRepository.docTheoID(tinhThanh.value!!.id)

            if (kq.getOrNull() != null) {
                dsQuanHuyen = kq.getOrNull() ?: emptyList()
            }
        }
    }

    fun docPhuongXa() {
        viewModelScope.launch {
            val kq = phuongXaRepository.docTheoID(quanHuyen.value!!.id)

            if (kq.getOrNull() != null) {
                dsPhuongXa = kq.getOrNull() ?: emptyList()
            }
        }
    }

    fun dangNhap(context: Context) {
        if (email.value.isBlank() || matKhau.value.isBlank()) {
            return
        }
        loading.value = true
        auth.signInWithEmailAndPassword(email.value, matKhau.value)
            .addOnCompleteListener(context as Activity) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                    viewModelScope.launch {
                        val kq = nguoiDungRepository.docTheoID(task.result.user!!.uid)
                        if (kq.getOrNull() != null) {
                            nguoiDung = kq.getOrNull()!!
                        } else {
                            Toast.makeText(context, "Lấy thông tin thất bại", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                } else {
                    Toast.makeText(
                        context,
                        "Đăng nhập thất bại. ${task.exception}",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
                loading.value = false
            }
    }

    fun dangKy(context: Context) {
        if (
            email.value.isBlank() || matKhau.value.isBlank()
            || xacNhanMatKhau.value.isBlank() || ten.value.isBlank()
            || sdt.value.isBlank() || soNha.value.isBlank()
            || tenDuong.value.isBlank()
        ) {
            Toast.makeText(
                context,
                "Vui lòng nhập đầy đủ thông tin",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        if (matKhau.value != xacNhanMatKhau.value) {
            Toast.makeText(
                context,
                "Mật khẩu xác nhận không trùng khớp",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        if (phuongXa.value == null) {
            Toast.makeText(
                context,
                "Vui lòng chọn phường xã",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        auth.createUserWithEmailAndPassword(
            email.value,
            matKhau.value
        )
            .addOnCompleteListener(context as Activity) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        context,
                        "Đăng ký thành công",
                        Toast.LENGTH_SHORT
                    ).show()
                    val nd = NguoiDung(
                        id = task.result.user!!.uid,
                        email = email.value,
                        so_dien_thoai = sdt.value,
                        ten = ten.value,
                        is_nam = isNam.value,
                        ngay_sinh = Date.from(
                            date.value.atStartOfDay(ZoneId.systemDefault()).toInstant()
                        ),
                        dia_chi = DiaChi(-1, soNha.value, ten.value, phuongXa.value!!)
                    )
                    viewModelScope.launch {
                        val kq = nguoiDungRepository.them(nd)
                        if (kq.getOrNull() != null) {
                            nguoiDung = kq.getOrNull()
                        } else {
                            Toast.makeText(
                                context,
                                "Thêm người dùng thất bại",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    Toast.makeText(
                        context,
                        "Đăng ký thất bại. ${task.exception}",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    suspend fun capNhatNguoiDung(): Boolean {
        val nd = NguoiDung(
            id = nguoiDung!!.id,
            email = email.value,
            so_dien_thoai = sdt.value,
            ten = ten.value,
            is_nam = isNam.value,
            ngay_sinh = Date.from(
                date.value.atStartOfDay(ZoneId.systemDefault()).toInstant()
            ),
            dia_chi = DiaChi(nguoiDung!!.dia_chi.id, soNha.value, ten.value, phuongXa.value!!)
        )

        val kq: Result<Boolean>

        return try {
            kq = nguoiDungRepository.capNhat(nd)
            kq.getOrNull() == true
        } catch (_: Exception) {
            false
        }
    }
}
