package com.example.appcsn.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.appcsn.data.model.NguoiDung
import com.example.appcsn.ui.PageHeader
import com.example.appcsn.ui.TransparentTextField
import com.example.appcsn.viewmodel.BaseViewModel
import com.example.appcsn.viewmodel.BaseViewModel.Companion.toLocalDate
import com.example.appcsn.viewmodel.TrangNguoiDungViewModel
import com.google.firebase.auth.FirebaseAuth
import com.ramcosta.composedestinations.annotation.Destination
import compose.icons.FeatherIcons
import compose.icons.feathericons.Eye
import compose.icons.feathericons.EyeOff
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalAnimationApi::class
)
@Destination
@Composable
fun TrangNguoiDung(
    nguoiDungViewModel: TrangNguoiDungViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var isDangKy by remember {
        mutableStateOf(false)
    }
    var isVisible by remember {
        mutableStateOf(false)
    }
    var isEditTen by remember {
        mutableStateOf(false)
    }
    var isEditSDT by remember {
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
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (isDatePicking) {
            DatePickerDialog(
                onDismissRequest = { isDatePicking = !isDatePicking },
                confirmButton = {
                    TextButton(onClick = {
                        nguoiDungViewModel.date.value =
                            if (dateState.selectedDateMillis != null)
                                toLocalDate(dateState.selectedDateMillis!!) else LocalDate.now()
                        isDatePicking = !isDatePicking
                    }) {
                        Text(text = "Chọn")
                    }
                }) {
                DatePicker(state = dateState)
            }
        }
        if (BaseViewModel.nguoiDung != null && nguoiDungViewModel.auth.currentUser != null) {
            PageHeader(text = "Thông tin người dùng")
            Text(
                text = "Email: ${BaseViewModel.nguoiDung!!.email}",
                modifier = Modifier.padding(15.dp)
            )
            HorizontalDivider()
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (isEditTen) {
                    TransparentTextField(text = "Tên người dùng", state = nguoiDungViewModel.ten)
                } else {
                    Text(
                        text = "Tên người dùng: ${nguoiDungViewModel.ten.value.ifEmpty { BaseViewModel.nguoiDung!!.ten }}",
                        modifier = Modifier.padding(15.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1F))
                IconButton(onClick = { isEditTen = !isEditTen }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Chỉnh sửa tên người dùng"
                    )
                }
            }
            HorizontalDivider()
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (isEditSDT) {
                    TransparentTextField(
                        text = "Số điện thoại",
                        state = nguoiDungViewModel.sdt,
                        keyboardType = KeyboardType.Number
                    )
                } else {
                    Text(
                        text = "Số điện thoại: ${nguoiDungViewModel.sdt.value.ifEmpty { BaseViewModel.nguoiDung!!.so_dien_thoai }}",
                        modifier = Modifier.padding(15.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1F))
                IconToggleButton(
                    checked = isEditSDT,
                    onCheckedChange = { isEditSDT = !isEditSDT }) {
                    Icon(
                        imageVector = if (isEditSDT) Icons.Default.Close else Icons.Default.Edit,
                        contentDescription = "Chỉnh sửa số điện thoại"
                    )
                }
            }
            HorizontalDivider()
            Text(
                text = "Địa chỉ: ${BaseViewModel.nguoiDung!!.dia_chi}",
                modifier = Modifier.padding(15.dp)
            )
            HorizontalDivider()
            val localDate = toLocalDate(BaseViewModel.nguoiDung!!.ngay_sinh)
            Row {
                Text(
                    text = "Ngày sinh: ${
                        if (nguoiDungViewModel.date.value != localDate)
                            nguoiDungViewModel.date.value.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                        else localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                    }",
                    modifier = Modifier.padding(15.dp)
                )
                Spacer(modifier = Modifier.weight(1F))
                IconToggleButton(
                    checked = isDatePicking,
                    onCheckedChange = { isDatePicking = !isDatePicking }) {
                    Icon(
                        imageVector = if (isDatePicking) Icons.Default.Close else Icons.Default.Edit,
                        contentDescription = "Chỉnh sửa ngày sinh"
                    )
                }
            }
            if (!soSanhNguoiDung(BaseViewModel.nguoiDung!!, nguoiDungViewModel)) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            nguoiDungViewModel.capNhatNguoiDung()
                        }
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text(text = "Lưu")
                }
            }
            Button(
                onClick = {
                    FirebaseAuth.getInstance().signOut()
                    BaseViewModel.nguoiDung = null
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(text = "Đăng xuất")
            }
        } else {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp)
            ) {
                item {
                    OutlinedTextField(
                        value = nguoiDungViewModel.email.value,
                        onValueChange = { nguoiDungViewModel.email.value = it },
                        label = { Text(text = "Email") },
                        shape = RoundedCornerShape(35.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    )
                    OutlinedTextField(
                        value = nguoiDungViewModel.pass.value,
                        onValueChange = { nguoiDungViewModel.pass.value = it },
                        label = { Text(text = "Mật khẩu") },
                        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { isVisible = !isVisible }) {
                                Icon(
                                    imageVector = if (isVisible)
                                        FeatherIcons.Eye
                                    else FeatherIcons.EyeOff,
                                    if (isVisible) "Hide password" else "Show password"
                                )
                            }
                        },
                        shape = RoundedCornerShape(35.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    )
                    transition.AnimatedVisibility(
                        visible = { isDangKy },
                        enter = expandVertically(),
                        exit = shrinkVertically()
                    ) {
                        Column {
                            OutlinedTextField(
                                value = nguoiDungViewModel.pass.value,
                                onValueChange = { nguoiDungViewModel.pass.value = it },
                                label = { Text(text = "Xác nhận mật khẩu") },
                                visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                trailingIcon = {
                                    IconButton(onClick = { isVisible = !isVisible }) {
                                        Icon(
                                            imageVector = if (isVisible)
                                                FeatherIcons.Eye
                                            else FeatherIcons.EyeOff,
                                            if (isVisible) "Hide password" else "Show password"
                                        )
                                    }
                                },
                                shape = RoundedCornerShape(35.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp)
                            )
                            OutlinedTextField(
                                value = nguoiDungViewModel.ten.value,
                                onValueChange = { nguoiDungViewModel.ten.value = it },
                                label = { Text(text = "Tên tài khoản") },
                                shape = RoundedCornerShape(35.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp)
                            )
                            OutlinedTextField(
                                value = nguoiDungViewModel.sdt.value,
                                onValueChange = { nguoiDungViewModel.sdt.value = it },
                                label = { Text(text = "Số điện thoại") },
                                shape = RoundedCornerShape(35.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp)
                            )
                            Text(
                                text = "Ngày sinh", modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp, horizontal = 15.dp)
                            )
                            OutlinedButton(
                                onClick = { isDatePicking = !isDatePicking },
                                shape = RoundedCornerShape(35.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp)
                            ) {
                                Text(text = nguoiDungViewModel.date.value.toString())
                            }
                            Text(
                                text = "Giới tính", modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp, horizontal = 15.dp)
                            )
                            Row(
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    RadioButton(
                                        selected = nguoiDungViewModel.isNam.value,
                                        onClick = { nguoiDungViewModel.isNam.value = true }
                                    )
                                    Text(text = "Nam")
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    RadioButton(
                                        selected = !nguoiDungViewModel.isNam.value,
                                        onClick = { nguoiDungViewModel.isNam.value = false }
                                    )
                                    Text(text = "Nữ")
                                }
                            }
                            ExposedDropdownMenuBox(
                                expanded = isChonTinhThanh,
                                onExpandedChange = {
                                    isChonTinhThanh = !isChonTinhThanh
                                }
                            ) {
                                OutlinedTextField(
                                    readOnly = true,
                                    value = if (nguoiDungViewModel.tinhThanh.value != null) nguoiDungViewModel.tinhThanh.value!!.ten else "Vui lòng chọn tỉnh thành",
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
                                        .padding(5.dp)
                                        .menuAnchor()
                                )
                                ExposedDropdownMenu(
                                    expanded = isChonTinhThanh,
                                    onDismissRequest = {
                                        isChonTinhThanh = false
                                    },
                                    modifier = Modifier
                                        .clip(shape = RoundedCornerShape(35.dp))
                                        .padding(5.dp)
                                ) {
                                    nguoiDungViewModel.dsTinhThanh.forEach { tinhThanh ->
                                        DropdownMenuItem(
                                            text = { Text(text = tinhThanh.ten) },
                                            onClick = {
                                                nguoiDungViewModel.tinhThanh.value = tinhThanh
                                                nguoiDungViewModel.docQuanHuyen()
                                                isChonTinhThanh = false
                                            })
                                    }
                                }
                            }
                            if (nguoiDungViewModel.tinhThanh.value != null) {
                                ExposedDropdownMenuBox(
                                    expanded = isChonQuanHuyen,
                                    onExpandedChange = {
                                        isChonQuanHuyen = !isChonQuanHuyen
                                    }
                                ) {
                                    OutlinedTextField(
                                        readOnly = true,
                                        value = if (nguoiDungViewModel.quanHuyen.value != null) nguoiDungViewModel.quanHuyen.value!!.ten else "Vui lòng chọn tỉnh thành",
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
                                        nguoiDungViewModel.dsQuanHuyen.forEach { quanHuyen ->
                                            DropdownMenuItem(
                                                text = { Text(text = quanHuyen.ten) },
                                                onClick = {
                                                    nguoiDungViewModel.quanHuyen.value = quanHuyen
                                                    nguoiDungViewModel.docPhuongXa()
                                                    isChonQuanHuyen = false
                                                })
                                        }
                                    }
                                }
                            }
                            if (nguoiDungViewModel.quanHuyen.value != null) {
                                ExposedDropdownMenuBox(
                                    expanded = isChonPhuongXa,
                                    onExpandedChange = {
                                        isChonPhuongXa = !isChonPhuongXa
                                    }
                                ) {
                                    OutlinedTextField(
                                        readOnly = true,
                                        value = if (nguoiDungViewModel.phuongXa.value != null) nguoiDungViewModel.phuongXa.value!!.ten else "Vui lòng chọn tỉnh thành",
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
                                            .padding(5.dp)
                                            .menuAnchor()
                                    )
                                    ExposedDropdownMenu(
                                        expanded = isChonPhuongXa,
                                        onDismissRequest = {
                                            isChonPhuongXa = false
                                        },
                                        modifier = Modifier
                                            .clip(shape = RoundedCornerShape(35.dp))
                                            .padding(5.dp)
                                    ) {
                                        nguoiDungViewModel.dsPhuongXa.forEach { phuongXa ->
                                            DropdownMenuItem(
                                                text = { Text(text = phuongXa.ten) },
                                                onClick = {
                                                    nguoiDungViewModel.phuongXa.value = phuongXa
                                                    isChonPhuongXa = false
                                                })
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Row {
                        Button(
                            onClick = { nguoiDungViewModel.dangNhap(context) }, modifier = Modifier
                                .weight(1F)
                                .padding(10.dp)
                        ) {
                            Text(text = "Đăng nhập")
                        }
                        Button(
                            onClick = {
                                isDangKy = if (isDangKy) {
                                    nguoiDungViewModel.dangKy(context)
                                    false
                                } else {
                                    true
                                }
                            },
                            modifier = Modifier
                                .weight(1F)
                                .padding(10.dp)
                        ) {
                            Text(text = "Đăng ký")
                        }
                    }
                }
            }
        }
    }
}

fun soSanhNguoiDung(nguoiDungGoc: NguoiDung, nguoiDungHienTai: TrangNguoiDungViewModel): Boolean {
    if (nguoiDungGoc.ten != nguoiDungHienTai.ten.value) {
        return false
    }
    if (toLocalDate(nguoiDungGoc.ngay_sinh) != nguoiDungHienTai.date.value) {
        return false
    }
    if (nguoiDungGoc.so_dien_thoai != nguoiDungHienTai.sdt.value) {
        return false
    }
    if (nguoiDungGoc.dia_chi.so_nha != nguoiDungHienTai.soNha.value
        || nguoiDungGoc.dia_chi.ten_duong != nguoiDungHienTai.tenDuong.value
        || nguoiDungGoc.dia_chi.phuong_xa.id != nguoiDungHienTai.phuongXa.value?.id
    ) {
        return false
    }
    return true
}
