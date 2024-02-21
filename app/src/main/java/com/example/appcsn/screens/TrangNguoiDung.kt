package com.example.appcsn.screens

import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appcsn.models.NguoiDung
import com.example.appcsn.viewmodels.NguoiDungViewModel
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import java.time.Instant
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class, DelicateCoroutinesApi::class)
@Destination
@Composable
fun TrangNguoiDung(
    nguoiDung: NguoiDung?,
) {
    val ndViewModel = viewModel<NguoiDungViewModel>()
    val context = LocalContext.current
    var isDangKy by remember {
        mutableStateOf(false)
    }
    var isVisible by remember {
        mutableStateOf(false)
    }
    var isDatePicking by remember {
        mutableStateOf(false)
    }
    var isChonTinhThanh by remember {
        mutableStateOf(false)
    }
    var isChonQuanHuyen by remember {
        mutableStateOf(false)
    }
    var isChonPhuongXa by remember {
        mutableStateOf(false)
    }
    val transition = updateTransition(isDangKy, label = "selected state")
    val dateState = rememberDatePickerState()
    LaunchedEffect(GlobalScope)
    {
        ndViewModel.docTinhThanh()
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
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    item {
                        OutlinedTextField(
                            value = ndViewModel.email.value,
                            onValueChange = { ndViewModel.email.value = it },
                            label = { Text(text = "Email") },
                            shape = RoundedCornerShape(35.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        )
                        OutlinedTextField(
                            value = ndViewModel.pass.value,
                            onValueChange = { ndViewModel.pass.value = it },
                            label = { Text(text = "Mật khẩu") },
                            visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                val image = if (isVisible)
                                    Icons.Default.Warning
                                else Icons.Default.Lock

                                // Please provide localized description for accessibility services
                                val description =
                                    if (isVisible) "Hide password" else "Show password"

                                IconButton(onClick = { isVisible = !isVisible }) {
                                    Icon(imageVector = image, description)
                                }
                            },
                            shape = RoundedCornerShape(35.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        )
                        Column {
                            OutlinedTextField(
                                value = ndViewModel.pass.value,
                                onValueChange = { ndViewModel.pass.value = it },
                                label = { Text(text = "Xác nhận mật khẩu") },
                                visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                trailingIcon = {
                                    val image = if (isVisible)
                                        Icons.Default.Warning
                                    else Icons.Default.Lock

                                    // Please provide localized description for accessibility services
                                    val description =
                                        if (isVisible) "Hide password" else "Show password"

                                    IconButton(onClick = { isVisible = !isVisible }) {
                                        Icon(imageVector = image, description)
                                    }
                                },
                                shape = RoundedCornerShape(35.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                            )
                            OutlinedTextField(
                                value = ndViewModel.ten.value,
                                onValueChange = { ndViewModel.ten.value = it },
                                label = { Text(text = "Tên tài khoản") },
                                shape = RoundedCornerShape(35.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                            )
                            OutlinedTextField(
                                value = ndViewModel.sdt.value,
                                onValueChange = { ndViewModel.sdt.value = it },
                                label = { Text(text = "Số điện thoại") },
                                shape = RoundedCornerShape(35.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                            )
                            Text(text = "Ngày sinh")
                            OutlinedButton(
                                onClick = { isDatePicking = !isDatePicking },
                                shape = RoundedCornerShape(35.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                            ) {
                                Text(text = ndViewModel.date.value.toString())
                            }
                            if (isDatePicking) {
                                DatePickerDialog(
                                    onDismissRequest = { isDatePicking = !isDatePicking },
                                    confirmButton = {
                                        TextButton(onClick = {
                                            ndViewModel.date.value =
                                                dateState.selectedDateMillis?.let {
                                                    Instant.ofEpochMilli(it).atZone(
                                                        ZoneId.systemDefault()
                                                    ).toLocalDate()
                                                }
                                            isDatePicking = !isDatePicking
                                        }) {
                                            Text(text = "Chọn")
                                        }
                                    }) {
                                    DatePicker(state = dateState)
                                }
                            }
                            Text(text = "Giới tính")
                            Row {
                                RadioButton(
                                    selected = ndViewModel.isNam.value,
                                    onClick = { ndViewModel.isNam.value = true }
                                )
                                Text(text = "Nam")
                                RadioButton(
                                    selected = !ndViewModel.isNam.value,
                                    onClick = { ndViewModel.isNam.value = false }
                                )
                                Text(text = "Nữ")
                            }
                            ExposedDropdownMenuBox(
                                expanded = isChonTinhThanh,
                                onExpandedChange = {
                                    isChonTinhThanh = !isChonTinhThanh
                                }
                            ) {
                                OutlinedTextField(
                                    readOnly = true,
                                    value = if (ndViewModel.tinhThanh.value != null) ndViewModel.tinhThanh.value!!.ten else "Vui lòng chọn tỉnh thành",
                                    onValueChange = { },
                                    label = { Text("Tỉnh thành") },
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                            expanded = isChonTinhThanh
                                        )
                                    },
                                    shape = RoundedCornerShape(35.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp)
                                        .menuAnchor()
                                )
                                ExposedDropdownMenu(
                                    expanded = isChonTinhThanh,
                                    onDismissRequest = {
                                        isChonTinhThanh = false
                                    },
                                    modifier = Modifier
                                        .clip(shape = RoundedCornerShape(35.dp))
                                        .padding(10.dp)
                                ) {
                                    ndViewModel.dsTinhThanh.forEach { tinhThanh ->
                                        DropdownMenuItem(
                                            text = { Text(text = tinhThanh.ten) },
                                            onClick = {
                                                ndViewModel.tinhThanh.value = tinhThanh
                                                ndViewModel.docQuanHuyen()
                                                isChonTinhThanh = false
                                            })
                                    }
                                }
                            }
                            if (ndViewModel.tinhThanh.value != null) {
                                ExposedDropdownMenuBox(
                                    expanded = isChonQuanHuyen,
                                    onExpandedChange = {
                                        isChonQuanHuyen = !isChonQuanHuyen
                                    }
                                ) {
                                    OutlinedTextField(
                                        readOnly = true,
                                        value = if (ndViewModel.quanHuyen.value != null) ndViewModel.quanHuyen.value!!.ten else "Vui lòng chọn tỉnh thành",
                                        onValueChange = { },
                                        label = { Text("Quận huyện") },
                                        trailingIcon = {
                                            ExposedDropdownMenuDefaults.TrailingIcon(
                                                expanded = isChonQuanHuyen
                                            )
                                        },
                                        shape = RoundedCornerShape(35.dp),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp)
                                            .menuAnchor()
                                    )
                                    ExposedDropdownMenu(
                                        expanded = isChonQuanHuyen,
                                        onDismissRequest = {
                                            isChonQuanHuyen = false
                                        },
                                        modifier = Modifier
                                            .clip(shape = RoundedCornerShape(35.dp))
                                            .padding(10.dp)
                                    ) {
                                        ndViewModel.dsQuanHuyen.forEach { quanHuyen ->
                                            DropdownMenuItem(
                                                text = { Text(text = quanHuyen.ten) },
                                                onClick = {
                                                    ndViewModel.quanHuyen.value = quanHuyen
                                                    ndViewModel.docPhuongXa()
                                                    isChonQuanHuyen = false
                                                })
                                        }
                                    }
                                }
                            }
                            if (ndViewModel.quanHuyen.value != null) {
                                ExposedDropdownMenuBox(
                                    expanded = isChonPhuongXa,
                                    onExpandedChange = {
                                        isChonPhuongXa = !isChonPhuongXa
                                    }
                                ) {
                                    OutlinedTextField(
                                        readOnly = true,
                                        value = if (ndViewModel.phuongXa.value != null) ndViewModel.phuongXa.value!!.ten else "Vui lòng chọn tỉnh thành",
                                        onValueChange = { },
                                        label = { Text("Phường xã") },
                                        trailingIcon = {
                                            ExposedDropdownMenuDefaults.TrailingIcon(
                                                expanded = isChonPhuongXa
                                            )
                                        },
                                        shape = RoundedCornerShape(35.dp),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp)
                                            .menuAnchor()
                                    )
                                    ExposedDropdownMenu(
                                        expanded = isChonPhuongXa,
                                        onDismissRequest = {
                                            isChonPhuongXa = false
                                        },
                                        modifier = Modifier
                                            .clip(shape = RoundedCornerShape(35.dp))
                                            .padding(10.dp)
                                    ) {
                                        ndViewModel.dsPhuongXa.forEach { phuongXa ->
                                            DropdownMenuItem(
                                                text = { Text(text = phuongXa.ten) },
                                                onClick = {
                                                    ndViewModel.phuongXa.value = phuongXa
                                                    isChonPhuongXa = false
                                                })
                                        }
                                    }
                                }
                            }
                        }
                        ElevatedButton(
                            onClick = { ndViewModel.dangNhap(context) }, modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        ) {
                            Text(text = "Đăng nhập")
                        }
                        ElevatedButton(
                            enabled = ndViewModel.phuongXa.value != null,
                            onClick = { ndViewModel.dangKy(context) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp)
                        ) {
                            Text(text = "Đăng ký")
                        }
                    }
                }
            }
        }
    }
}
