package com.example.appcsn.viewmodel

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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
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
    var daDangNhap = mutableStateOf(false)
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
    var ngaySinh =
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
        ngaySinh.value = LocalDate.now()
        tenDuong.value = ""
        soNha.value = ""
        tinhThanh.value = null
        quanHuyen.value = null
        phuongXa.value = null
    }

    private fun fill() {
        ten.value = nguoiDung!!.ten
        sdt.value = nguoiDung!!.so_dien_thoai
        ngaySinh.value = nguoiDung!!.ngay_sinh.toInstant()
            .atZone(
                ZoneId.systemDefault()
            ).toLocalDate()
        soNha.value = nguoiDung!!.dia_chi.so_nha
        tenDuong.value = nguoiDung!!.dia_chi.ten_duong
        phuongXa.value = nguoiDung!!.dia_chi.phuong_xa
    }

    suspend fun docNguoiDungFirebase() {
        loading.value = true
        val user = Firebase.auth.currentUser
        daDangNhap.value = user != null
        if (user != null && user.isEmailVerified) {
            val job = viewModelScope.launch {
                nguoiDung = nguoiDungRepository.doc(user.uid).getOrNull()
            }
            job.join()
            fill()
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
        when {
            email.value.isBlank() -> Toast.makeText(
                context,
                "Email không được trống",
                Toast.LENGTH_SHORT
            ).show()

            matKhau.value.isBlank() -> Toast.makeText(
                context,
                "Mật khẩu không được trống",
                Toast.LENGTH_SHORT
            ).show()

            else -> {
                Firebase.auth.signInWithEmailAndPassword(email.value, matKhau.value)
                    .addOnSuccessListener {
                        viewModelScope.launch {
                            docNguoiDungFirebase()
                            Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                    .addOnFailureListener { exception ->
                        when (exception::class.java) {
                            FirebaseAuthInvalidUserException::class.java -> Toast.makeText(
                                context,
                                "Email không chính xác",
                                Toast.LENGTH_SHORT
                            ).show()

                            FirebaseAuthInvalidCredentialsException::class.java -> Toast.makeText(
                                context,
                                "Mật khẩu không chính xác",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }

    fun dangKy(context: Context) {
        when {
            email.value.isBlank() -> Toast.makeText(
                context,
                "Email không được trống",
                Toast.LENGTH_SHORT
            ).show()

            matKhau.value.isBlank() -> Toast.makeText(
                context,
                "Mật khẩu không được trống",
                Toast.LENGTH_SHORT
            ).show()

            xacNhanMatKhau.value.isBlank() -> Toast.makeText(
                context,
                "Mật khẩu xác nhận không được trống",
                Toast.LENGTH_SHORT
            ).show()

            ten.value.isBlank() -> Toast.makeText(
                context,
                "Tên tài khoản không được trống",
                Toast.LENGTH_SHORT
            ).show()

            sdt.value.isBlank() -> Toast.makeText(
                context,
                "Số điện thoại không được trống",
                Toast.LENGTH_SHORT
            ).show()

            soNha.value.isBlank() -> Toast.makeText(
                context,
                "Số nhà không được trống",
                Toast.LENGTH_SHORT
            ).show()

            tenDuong.value.isBlank() -> Toast.makeText(
                context,
                "Tên đường không được trống",
                Toast.LENGTH_SHORT
            ).show()

            matKhau.value != xacNhanMatKhau.value -> Toast.makeText(
                context,
                "Mật khẩu xác nhận không trùng khớp",
                Toast.LENGTH_SHORT
            ).show()

            phuongXa.value == null -> Toast.makeText(
                context,
                "Hãy chọn phường xã nơi bạn ở",
                Toast.LENGTH_SHORT
            ).show()

            else -> {
                Firebase.auth.createUserWithEmailAndPassword(email.value, matKhau.value)
                    .addOnSuccessListener { task ->
                        val nd = NguoiDung(
                            id = task.user!!.uid,
                            email = email.value,
                            so_dien_thoai = sdt.value,
                            ten = ten.value,
                            is_nam = isNam.value,
                            ngay_sinh = Date.from(
                                ngaySinh.value.atStartOfDay(ZoneId.systemDefault()).toInstant()
                            ),
                            dia_chi = DiaChi(-1, soNha.value, ten.value, phuongXa.value!!)
                        )
                        task.user!!.sendEmailVerification()
                        viewModelScope.launch {
                            val kq = nguoiDungRepository.them(nd)
                            if (kq.getOrNull() != null) {
                                docNguoiDungFirebase()
                                Toast.makeText(
                                    context,
                                    "Đăng nhập thành công",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                    .addOnFailureListener { exception ->
                        when (exception::class.java) {
                            FirebaseAuthWeakPasswordException::class.java -> Toast.makeText(
                                context,
                                "Mật khẩu yếu",
                                Toast.LENGTH_SHORT
                            ).show()

                            FirebaseAuthInvalidCredentialsException::class.java -> Toast.makeText(
                                context,
                                "Mật khẩu không phù hợp",
                                Toast.LENGTH_SHORT
                            ).show()

                            FirebaseAuthUserCollisionException::class.java -> Toast.makeText(
                                context,
                                "Email đã tồn tại",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
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
                ngaySinh.value.atStartOfDay(ZoneId.systemDefault()).toInstant()
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
