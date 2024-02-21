package com.example.appcsn.viewmodels

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.appcsn.HttpHelper
import com.example.appcsn.models.DiaChi
import com.example.appcsn.models.NguoiDung
import com.example.appcsn.models.PhuongXa
import com.example.appcsn.models.QuanHuyen
import com.example.appcsn.models.TinhThanh
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

class NguoiDungViewModel : BaseViewModel() {
    val auth = Firebase.auth
    var email =
        mutableStateOf("")
    var pass =
        mutableStateOf("")
    var ten =
        mutableStateOf("")
    var sdt =
        mutableStateOf("")
    var isNam =
        mutableStateOf(true)
    var date =
        mutableStateOf(LocalDate.now())
    var tinhThanh =
        mutableStateOf<TinhThanh?>(null)
    var quanHuyen =
        mutableStateOf<QuanHuyen?>(null)
    var phuongXa =
        mutableStateOf<PhuongXa?>(null)
    var dsTinhThanh = listOf<TinhThanh>()
    var dsQuanHuyen = listOf<QuanHuyen>()
    var dsPhuongXa = listOf<PhuongXa>()
    private val nguoiDungAPI: NguoiDung.API =
        HttpHelper.DacSanAPI.getInstance().create(NguoiDung.API::class.java)

    fun docTinhThanh() {
        val api =
            HttpHelper.DacSanAPI.getInstance().create(TinhThanh.docAPI::class.java)
        viewModelScope.launch {
            val kq = api.docDanhSach()

            if (kq.body() != null) {
                dsTinhThanh = kq.body()!!
            }
        }
    }

    fun docQuanHuyen() {
        val api =
            HttpHelper.DacSanAPI.getInstance().create(QuanHuyen.Doc::class.java)
        viewModelScope.launch {
            val kq = api.docDanhSach(tinhThanh.value!!.id)

            if (kq.body() != null) {
                dsQuanHuyen = kq.body()!!
            }
        }
    }

    fun docPhuongXa() {
        val api =
            HttpHelper.DacSanAPI.getInstance().create(PhuongXa.Doc::class.java)
        viewModelScope.launch {
            val kq = api.docDanhSach(quanHuyen.value!!.id)

            if (kq.body() != null) {
                dsPhuongXa = kq.body()!!
            }
        }
    }

    fun dangNhap(context: Context): FirebaseUser? {
        if (email.value.isEmpty() || pass.value.isEmpty()) {
            return null
        }
        auth.signInWithEmailAndPassword(email.value, pass.value)
            .addOnCompleteListener(context as Activity) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                    viewModelScope.launch {
                        val kq = nguoiDungAPI.doc(task.result.user!!.uid)
                        if (kq.body() != null) {
                            signIn(kq.body()!!)
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
            }

        return auth.currentUser
    }

    fun dangKy(context: Context) {
        auth.createUserWithEmailAndPassword(
            email.value,
            pass.value
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
                        dia_chi = DiaChi(-1, "Nhà không số", "Đường không tên", phuongXa.value!!)
                    )
                    viewModelScope.launch {
                        val kq = nguoiDungAPI.them(nd)
                        if (kq.body() != null) {
                            Toast.makeText(
                                context,
                                "Thêm người dùng thành công",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                context,
                                "Thêm người dùng thất bại",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } else {
                    Log.w(
                        "Auth",
                        "createUserWithEmail:failure",
                        task.exception
                    )
                    Toast.makeText(
                        context,
                        "Đăng ký thất bại. ${task.exception}",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }
}
