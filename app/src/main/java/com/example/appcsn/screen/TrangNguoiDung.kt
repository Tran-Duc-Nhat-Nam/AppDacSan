package com.example.appcsn.screen

import android.os.Handler
import android.os.Looper
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.appcsn.data.model.NguoiDung
import com.example.appcsn.ui.navgraph.SettingGraph
import com.example.appcsn.ui.widget.PageHeader
import com.example.appcsn.ui.widget.TransparentTextField
import com.example.appcsn.viewmodel.BaseViewModel
import com.example.appcsn.viewmodel.BaseViewModel.Companion.nguoiDung
import com.example.appcsn.viewmodel.BaseViewModel.Companion.toLocalDate
import com.example.appcsn.viewmodel.TrangNguoiDungViewModel
import com.google.firebase.auth.FirebaseAuth
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import compose.icons.FeatherIcons
import compose.icons.feathericons.Eye
import compose.icons.feathericons.EyeOff
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(
    ExperimentalMaterial3Api::class,
)
@Destination<SettingGraph>()
@Composable
fun TrangNguoiDung(
    navigator: DestinationsNavigator,
    nguoiDungViewModel: TrangNguoiDungViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val mainHandler = Handler(Looper.getMainLooper())

    val kiemTraXacMinh = object : Runnable {
        override fun run() {
            if (FirebaseAuth.getInstance().currentUser != null) {
                if (FirebaseAuth.getInstance().currentUser!!.isEmailVerified) {
                    coroutineScope.launch {
                        nguoiDungViewModel.docNguoiDungFirebase()
                    }
                    mainHandler.removeCallbacks(this)
                } else {
                    FirebaseAuth.getInstance().currentUser!!.reload()
                    mainHandler.postDelayed(this, 1000)
                }
            }
        }
    }

    BackHandler {
        BaseViewModel.dsNavItem[2].backStack.removeLast()
        navigator.navigate(BaseViewModel.dsNavItem[2].backStack.last())
    }

    val isDangKy = remember {
        mutableStateOf(false)
    }
    val isVisible = remember {
        mutableStateOf(false)
    }
    val isEditTen = remember {
        mutableStateOf(false)
    }
    val isEditSDT = remember {
        mutableStateOf(false)
    }
    val isDatePicking = remember {
        mutableStateOf(false)
    }
    val isChonTinhThanh = remember {
        mutableStateOf(false)
    }
    val isChonQuanHuyen = remember {
        mutableStateOf(false)
    }
    val isChonPhuongXa = remember {
        mutableStateOf(false)
    }
    val transition = updateTransition(isDangKy, label = "selected state")
    val dateState = rememberDatePickerState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        DialogChonNgaySinh(isDatePicking, nguoiDungViewModel, dateState)
        if (nguoiDung == null) {
            if (nguoiDungViewModel.daDangNhap.value) {
                mainHandler.post(kiemTraXacMinh)
                ManHinhXacMinh(nguoiDungViewModel)
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.Center,
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
                        TextFieldNhapMatKhau(nguoiDungViewModel, isVisible)
                        KhuVucNhapThongTin(
                            transition,
                            isDangKy,
                            nguoiDungViewModel,
                            isVisible,
                            isDatePicking,
                            isChonTinhThanh,
                            isChonQuanHuyen,
                            isChonPhuongXa
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                onClick = {
                                    nguoiDungViewModel.dangNhap(context)
                                },
                                modifier = Modifier
                                    .padding(10.dp)
                            ) {
                                Text(text = "Đăng nhập")
                            }
                            Button(
                                onClick = {
                                    isDangKy.value = if (isDangKy.value) {
                                        nguoiDungViewModel.dangKy(context)
                                        false
                                    } else {
                                        nguoiDungViewModel.clear()
                                        true
                                    }
                                },
                                modifier = Modifier
                                    .padding(10.dp)
                            ) {
                                Text(text = "Đăng ký")
                            }
                        }
                    }
                }
            }
        } else {
            PageHeader(text = "Thông tin người dùng")
            Text(
                text = "Email: ${nguoiDung!!.email}",
                modifier = Modifier.padding(15.dp)
            )
            HorizontalDivider()
            TenNguoiDung(isEditTen, nguoiDungViewModel)
            HorizontalDivider()
            SoDienThoai(isEditSDT, nguoiDungViewModel)
            HorizontalDivider()
            Text(
                text = "Địa chỉ: ${nguoiDung!!.dia_chi}",
                modifier = Modifier.padding(15.dp)
            )
            HorizontalDivider()
            NgaySinh(nguoiDungViewModel, isDatePicking)
            ButtonLuu(nguoiDungViewModel, coroutineScope)
            Spacer(modifier = Modifier.height(15.dp))
            ButtonDangXuat(nguoiDungViewModel)
        }
    }
}

@Composable
private fun ButtonLuu(
    nguoiDungViewModel: TrangNguoiDungViewModel,
    coroutineScope: CoroutineScope
) {
    if (!soSanhNguoiDung(nguoiDung!!, nguoiDungViewModel)) {
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
}

@Composable
private fun NgaySinh(
    nguoiDungViewModel: TrangNguoiDungViewModel,
    isDatePicking: MutableState<Boolean>
) {
    val localDate = toLocalDate(nguoiDung!!.ngay_sinh)
    Row {
        Text(
            text = "Ngày sinh: ${
                if (nguoiDungViewModel.ngaySinh.value != localDate)
                    nguoiDungViewModel.ngaySinh.value.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                else localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
            }",
            modifier = Modifier.padding(15.dp)
        )
        Spacer(modifier = Modifier.weight(1F))
        IconToggleButton(
            checked = isDatePicking.value,
            onCheckedChange = { isDatePicking.value = !isDatePicking.value }) {
            Icon(
                imageVector = if (isDatePicking.value) Icons.Default.Close else Icons.Default.Edit,
                contentDescription = "Chỉnh sửa ngày sinh"
            )
        }
    }
}

@Composable
private fun SoDienThoai(
    isEditSDT: MutableState<Boolean>,
    nguoiDungViewModel: TrangNguoiDungViewModel
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        if (isEditSDT.value) {
            TransparentTextField(
                text = "Số điện thoại",
                state = nguoiDungViewModel.sdt,
                keyboardType = KeyboardType.Number
            )
        } else {
            Text(
                text = "Số điện thoại: ${nguoiDungViewModel.sdt.value.ifEmpty { nguoiDung!!.so_dien_thoai }}",
                modifier = Modifier.padding(15.dp)
            )
        }
        Spacer(modifier = Modifier.weight(1F))
        IconToggleButton(
            checked = isEditSDT.value,
            onCheckedChange = { isEditSDT.value = !isEditSDT.value }) {
            Icon(
                imageVector = if (isEditSDT.value) Icons.Default.Close else Icons.Default.Edit,
                contentDescription = "Chỉnh sửa số điện thoại"
            )
        }
    }
}

@Composable
private fun TenNguoiDung(
    isEditTen: MutableState<Boolean>,
    nguoiDungViewModel: TrangNguoiDungViewModel
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        if (isEditTen.value) {
            TransparentTextField(
                text = "Tên người dùng",
                state = nguoiDungViewModel.ten
            )
        } else {
            Text(
                text = "Tên người dùng: ${nguoiDungViewModel.ten.value.ifEmpty { nguoiDung!!.ten }}",
                modifier = Modifier.padding(15.dp)
            )
        }
        Spacer(modifier = Modifier.weight(1F))
        IconButton(onClick = { isEditTen.value = !isEditTen.value }) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Chỉnh sửa tên người dùng"
            )
        }
    }
}

@Composable
private fun ManHinhXacMinh(nguoiDungViewModel: TrangNguoiDungViewModel) {
    Button(
        onClick = {
            FirebaseAuth.getInstance().currentUser!!.sendEmailVerification()
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(text = "Gửi lại email xác nhận")
    }
    Spacer(modifier = Modifier.height(15.dp))
    ButtonDangXuat(nguoiDungViewModel)
}

@Composable
private fun TextFieldNhapMatKhau(
    nguoiDungViewModel: TrangNguoiDungViewModel,
    isVisible: MutableState<Boolean>
) {
    OutlinedTextField(
        value = nguoiDungViewModel.matKhau.value,
        onValueChange = { nguoiDungViewModel.matKhau.value = it },
        label = { Text(text = "Mật khẩu") },
        visualTransformation = if (isVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { isVisible.value = !isVisible.value }) {
                Icon(
                    imageVector = if (isVisible.value)
                        FeatherIcons.Eye
                    else FeatherIcons.EyeOff,
                    contentDescription = if (isVisible.value) "Hide password" else "Show password"
                )
            }
        },
        shape = RoundedCornerShape(35.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    )
}

@Composable
@OptIn(ExperimentalAnimationApi::class)
private fun KhuVucNhapThongTin(
    transition: Transition<MutableState<Boolean>>,
    isDangKy: MutableState<Boolean>,
    nguoiDungViewModel: TrangNguoiDungViewModel,
    isVisible: MutableState<Boolean>,
    isDatePicking: MutableState<Boolean>,
    isChonTinhThanh: MutableState<Boolean>,
    isChonQuanHuyen: MutableState<Boolean>,
    isChonPhuongXa: MutableState<Boolean>
) {
    transition.AnimatedVisibility(
        visible = { isDangKy.value },
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        Column {
            TextFieldXacNhanMatKhau(nguoiDungViewModel, isVisible)
            OutlinedTextField(
                value = nguoiDungViewModel.ten.value,
                onValueChange = { nguoiDungViewModel.ten.value = it },
                label = { Text(text = "Tên người dùng") },
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
            ChonNgaySinh(isDatePicking, nguoiDungViewModel)
            ChonGioiTinh(nguoiDungViewModel)
            ChonDiaChi(
                isChonTinhThanh,
                isChonQuanHuyen,
                isChonPhuongXa,
                nguoiDungViewModel
            )
        }
    }
}

@Composable
private fun TextFieldXacNhanMatKhau(
    nguoiDungViewModel: TrangNguoiDungViewModel,
    isVisible: MutableState<Boolean>
) {
    OutlinedTextField(
        value = nguoiDungViewModel.xacNhanMatKhau.value,
        onValueChange = {
            nguoiDungViewModel.xacNhanMatKhau.value = it
        },
        label = { Text(text = "Xác nhận mật khẩu") },
        visualTransformation = if (isVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { isVisible.value = !isVisible.value }) {
                Icon(
                    imageVector = if (isVisible.value)
                        FeatherIcons.Eye
                    else FeatherIcons.EyeOff,
                    if (isVisible.value) "Hide password" else "Show password"
                )
            }
        },
        shape = RoundedCornerShape(35.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    )
}

@Composable
private fun ChonNgaySinh(
    isDatePicking: MutableState<Boolean>,
    nguoiDungViewModel: TrangNguoiDungViewModel
) {
    Text(
        text = "Ngày sinh", modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 15.dp)
    )
    OutlinedButton(
        onClick = { isDatePicking.value = !isDatePicking.value },
        shape = RoundedCornerShape(35.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text(text = nguoiDungViewModel.ngaySinh.value.toString())
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun DialogChonNgaySinh(
    isDatePicking: MutableState<Boolean>,
    nguoiDungViewModel: TrangNguoiDungViewModel,
    dateState: DatePickerState
) {
    if (isDatePicking.value) {
        DatePickerDialog(
            onDismissRequest = { isDatePicking.value = !isDatePicking.value },
            confirmButton = {
                TextButton(onClick = {
                    nguoiDungViewModel.ngaySinh.value =
                        if (dateState.selectedDateMillis != null)
                            toLocalDate(dateState.selectedDateMillis!!) else LocalDate.now()
                    isDatePicking.value = !isDatePicking.value
                }) {
                    Text(text = "Chọn")
                }
            }) {
            DatePicker(state = dateState)
        }
    }
}

@Composable
private fun ChonGioiTinh(nguoiDungViewModel: TrangNguoiDungViewModel) {
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
}

@Composable
private fun ButtonDangXuat(nguoiDungViewModel: TrangNguoiDungViewModel) {
    Button(
        onClick = {
            FirebaseAuth.getInstance().signOut()
            nguoiDung = null
            nguoiDungViewModel.daDangNhap.value = false
            nguoiDungViewModel.clear()
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(text = "Đăng xuất")
    }
}

fun soSanhNguoiDung(nguoiDungGoc: NguoiDung, nguoiDungHienTai: TrangNguoiDungViewModel): Boolean {
    if (nguoiDungGoc.ten != nguoiDungHienTai.ten.value) {
        return false
    }
    if (toLocalDate(nguoiDungGoc.ngay_sinh) != nguoiDungHienTai.ngaySinh.value) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChonDiaChi(
    isChonTinhThanh: MutableState<Boolean>,
    isChonQuanHuyen: MutableState<Boolean>,
    isChonPhuongXa: MutableState<Boolean>,
    nguoiDungViewModel: TrangNguoiDungViewModel
) {
    ExposedDropdownMenuBox(
        expanded = isChonTinhThanh.value,
        onExpandedChange = {
            isChonTinhThanh.value = !isChonTinhThanh.value
        }
    ) {
        OutlinedTextField(
            readOnly = true,
            value = if (nguoiDungViewModel.tinhThanh.value != null) nguoiDungViewModel.tinhThanh.value!!.ten else "Vui lòng chọn tỉnh thành",
            onValueChange = { },
            label = { Text("Tỉnh thành") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = isChonTinhThanh.value
                )
            },
            shape = RoundedCornerShape(35.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = isChonTinhThanh.value,
            onDismissRequest = {
                isChonTinhThanh.value = false
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
                        isChonTinhThanh.value = false
                    })
            }
        }
    }
    if (nguoiDungViewModel.tinhThanh.value != null) {
        ExposedDropdownMenuBox(
            expanded = isChonQuanHuyen.value,
            onExpandedChange = {
                isChonQuanHuyen.value = !isChonQuanHuyen.value
            }
        ) {
            OutlinedTextField(
                readOnly = true,
                value = if (nguoiDungViewModel.quanHuyen.value != null)
                    nguoiDungViewModel.quanHuyen.value!!.ten
                else "Vui lòng chọn quận huyện",
                onValueChange = { },
                label = { Text("Quận huyện") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = isChonQuanHuyen.value
                    )
                },
                shape = RoundedCornerShape(35.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = isChonQuanHuyen.value,
                onDismissRequest = {
                    isChonQuanHuyen.value = false
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
                            isChonQuanHuyen.value = false
                        })
                }
            }
        }
    }
    if (nguoiDungViewModel.quanHuyen.value != null) {
        ExposedDropdownMenuBox(
            expanded = isChonPhuongXa.value,
            onExpandedChange = {
                isChonPhuongXa.value = !isChonPhuongXa.value
            }
        ) {
            OutlinedTextField(
                readOnly = true,
                value = if (nguoiDungViewModel.phuongXa.value != null)
                    nguoiDungViewModel.phuongXa.value!!.ten
                else "Vui lòng chọn phường xã",
                onValueChange = { },
                label = { Text("Phường xã") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = isChonPhuongXa.value
                    )
                },
                shape = RoundedCornerShape(35.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = isChonPhuongXa.value,
                onDismissRequest = {
                    isChonPhuongXa.value = false
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
                            isChonPhuongXa.value = false
                        })
                }
            }
        }
    }
    if (nguoiDungViewModel.phuongXa.value != null) {
        OutlinedTextField(
            value = nguoiDungViewModel.soNha.value,
            onValueChange = { nguoiDungViewModel.soNha.value = it },
            label = { Text(text = "Số nhà") },
            shape = RoundedCornerShape(35.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )
        OutlinedTextField(
            value = nguoiDungViewModel.tenDuong.value,
            onValueChange = { nguoiDungViewModel.tenDuong.value = it },
            label = { Text(text = "Tên đường") },
            shape = RoundedCornerShape(35.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )
    }
}
