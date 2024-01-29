package com.example.appcsn.screens

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appcsn.models.NguoiDung
import com.example.appcsn.viewmodels.NguoiDungViewModel
import com.ramcosta.composedestinations.annotation.Destination

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun TrangNguoiDung(
    nguoiDung: NguoiDung?,
) {
    val ndViewModel = viewModel<NguoiDungViewModel>()
    val context = LocalContext.current
    var isDangNhap by remember {
        mutableStateOf(false)
    }
    var isDangKy by remember {
        mutableStateOf(false)
    }
    Column(
        Modifier
            .fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
        {
            if (nguoiDung != null && ndViewModel.auth.currentUser != null) {
                Text(text = nguoiDung.id)
                Text(text = nguoiDung.email)
                Text(text = nguoiDung.ten)
                Text(text = nguoiDung.so_dien_thoai)
                Text(text = nguoiDung.dia_chi.toString())
                Text(text = nguoiDung.ngay_sinh.toString())
            } else {
                Column {
                    if (isDangNhap) {
                        AlertDialog(
                            onDismissRequest = {
                                isDangNhap = false
                            }
                        ) {
                            Surface(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .wrapContentHeight(),
                                shape = MaterialTheme.shapes.large
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    TextField(
                                        value = ndViewModel.email.value,
                                        onValueChange = { ndViewModel.email.value = it},
                                        modifier = Modifier
                                            .padding(5.dp)
                                            .clip(shape = RoundedCornerShape(25.dp))
                                        )
                                    TextField(value = ndViewModel.pass.value,
                                        onValueChange = { ndViewModel.pass.value = it},
                                        modifier = Modifier
                                            .padding(5.dp)
                                            .clip(shape = RoundedCornerShape(25.dp)))
                                    TextButton(onClick = {ndViewModel.dangNhap(context)}) {
                                        Text(text = "Đăng nhập")
                                    }
                                }
                            }
                        }
                    }
                    if (isDangKy) {
                        AlertDialog(
                            onDismissRequest = {
                                isDangKy = false
                            }
                        ) {
                            Surface(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .wrapContentHeight(),
                                shape = MaterialTheme.shapes.large
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    TextField(
                                        value = ndViewModel.email.value,
                                        onValueChange = { ndViewModel.email.value = it},
                                        modifier = Modifier
                                            .padding(5.dp)
                                            .clip(shape = RoundedCornerShape(25.dp))
                                    )
                                    TextField(
                                        value = ndViewModel.pass.value,
                                        onValueChange = { ndViewModel.pass.value = it},
                                        modifier = Modifier
                                            .padding(5.dp)
                                            .clip(shape = RoundedCornerShape(25.dp)))
                                    TextButton(onClick = {ndViewModel.auth.createUserWithEmailAndPassword(ndViewModel.email.value, ndViewModel.pass.value)
                                        .addOnCompleteListener(context as Activity) { task ->
                                            if (task.isSuccessful) {
                                                // Sign in success, update UI with the signed-in user's information
                                                Toast.makeText(context, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
                                                val user = ndViewModel.auth.currentUser
                                            } else {
                                                // If sign in fails, display a message to the user.
                                                Log.w("Auth", "createUserWithEmail:failure", task.exception)
                                                Toast.makeText(
                                                    context,
                                                    "Đăng ký thất bại. ${task.exception}",
                                                    Toast.LENGTH_SHORT,
                                                ).show()
                                            }
                                        }}) {
                                        Text(text = "Đăng ký")
                                    }
                                }
                            }
                        }
                    }
                    ElevatedButton(
                        onClick = {
                            if (!isDangNhap) {
                                isDangNhap = true
                            }
                                  },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Đăng nhập")
                    }
                    ElevatedButton(
                        onClick = {
                            if (!isDangKy) {
                                isDangKy = true
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Đăng ký")
                    }
                }
            }
        }
    }
}