package com.example.appcsn.features.noiban.ui.view

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcsn.core.ui.view.TrangBaoLoi
import com.example.appcsn.core.ui.viewmodel.BaseViewModel.Companion.dsNavItem
import com.example.appcsn.core.ui.viewmodel.BaseViewModel.Companion.nguoiDung
import com.example.appcsn.core.ui.widget.CircleProgressIndicator
import com.example.appcsn.features.noiban.data.LuotDanhGiaNoiBan
import com.example.appcsn.features.noiban.ui.nav.PlaceGraph
import com.example.appcsn.features.noiban.ui.viewmodel.TrangChiTietNoiBanViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Destination<PlaceGraph>()
@Composable
fun TrangChiTietNoiBan(
    navigator: DestinationsNavigator,
    viewModel: TrangChiTietNoiBanViewModel,
    id: Int,
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val maxLineMT = remember {
        mutableIntStateOf(4)
    }
    val isExpandableMT = remember {
        mutableStateOf(false)
    }
    val isExpandableDG = remember {
        mutableStateOf(false)
    }
    val isEdit = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(true) {
        viewModel.docDuLieu(id)
    }

    BackHandler {
        dsNavItem[1].backStack.removeLast()
        navigator.navigate(dsNavItem[1].backStack.last())
    }

    if (viewModel.loading.value) {
        CircleProgressIndicator()
    } else if (viewModel.noiBan.value == null) {
        TrangBaoLoi("Không tìm được thông tin chi tiết của đặc sản")
    } else {
        Column {
            Surface(
                shadowElevation = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                ThanhTieuDe(viewModel, coroutineScope, context)
            }
            Spacer(modifier = Modifier.height(20.dp))
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp)
            ) {
                item {
                    ThanhThongKeTuongTac(viewModel)
                    Spacer(modifier = Modifier.height(15.dp))
                    ThanhMoTa(viewModel, maxLineMT, isExpandableMT)
                    Spacer(modifier = Modifier.height(15.dp))
                    ThanhDiaChi(viewModel)
                    Spacer(modifier = Modifier.height(15.dp))
                    KhuVucDanhGia(isEdit, viewModel, coroutineScope)
                    Spacer(modifier = Modifier.height(10.dp))
                    DanhSachDanhGia(coroutineScope, viewModel, isExpandableDG, isEdit)
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
        }
    }
}

@Composable
private fun KhuVucDanhGia(
    isEdit: MutableState<Boolean>,
    viewModel: TrangChiTietNoiBanViewModel,
    coroutineScope: CoroutineScope
) {
    if (nguoiDung != null || isEdit.value) {
        if (viewModel.luotDanhGia == null || isEdit.value) {
            ThanhDanhGia(
                viewModel,
                coroutineScope,
                isEdit
            )
        } else {
            DanhGia(
                viewModel,
                coroutineScope,
                viewModel.luotDanhGia!!,
                nguoiDung!!.ten,
                isEdit
            )
        }
    }
}

@Composable
private fun DanhSachDanhGia(
    coroutineScope: CoroutineScope,
    viewModel: TrangChiTietNoiBanViewModel,
    isExpandDG: MutableState<Boolean>,
    isEdit: MutableState<Boolean>,
) {
    TextButton(onClick = {
        if (isExpandDG.value) {
            isExpandDG.value = false
        } else {
            coroutineScope.launch {
                viewModel.docDanhSachDanhGia()
                isExpandDG.value = true
            }
        }
    }) {
        Text(text = "Xem nhật xét của người dùng khác")
    }
    if (isExpandDG.value) {
        if (viewModel.dsDanhGiaNoiBan.isNotEmpty()) {
            viewModel.dsDanhGiaNoiBan.forEach { cmt ->
                if (cmt.key.id_nguoi_dung != (nguoiDung?.id ?: "null")) {
                    Spacer(modifier = Modifier.height(10.dp))
                    DanhGia(
                        viewModel,
                        coroutineScope,
                        cmt.key,
                        cmt.value,
                        isEdit
                    )
                }
            }
        } else {
            Text(text = "Chưa có đánh giá nào khác", Modifier.padding(vertical = 10.dp))
        }
    }
}

@Composable
private fun DanhGia(
    viewModel: TrangChiTietNoiBanViewModel,
    coroutineScope: CoroutineScope,
    luotDanhGia: LuotDanhGiaNoiBan,
    ten: String,
    isEdit: MutableState<Boolean>,
) {
    val isExpand = remember {
        mutableStateOf(false)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(corner = CornerSize(10.dp)))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(5.dp)
    ) {
        Column(modifier = Modifier.weight(1F)) {
            Text(
                text = if (luotDanhGia.id_nguoi_dung == (nguoiDung?.id
                        ?: "null")
                ) "Đánh giá của bạn"
                else "Người dùng $ten",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(5.dp)
            )


            Text(
                text = luotDanhGia.noi_dung!!,
                modifier = Modifier.padding(5.dp)
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .wrapContentWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(5.dp)
            ) {
                Text(text = luotDanhGia.diem_danh_gia.toString())
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Đánh giá",
                    tint = Color.Yellow,
                    modifier = Modifier.size(18.dp)
                )
            }
            IconButton(onClick = { isEdit.value = !isEdit.value }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Tùy chọn",
                    modifier = Modifier
                        .size(36.dp)
                        .padding(5.dp)
                )
            }
            DropdownMenu(
                expanded = isExpand.value,
                onDismissRequest = { isEdit.value = false }
            ) {
                if (luotDanhGia.id_nguoi_dung == (nguoiDung?.id ?: "null")) {
                    DropdownMenuItem(
                        text = { Text("Chỉnh sửa") },
                        onClick = {
                            isEdit.value = !isEdit.value
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Xóa") },
                        onClick = {
                            coroutineScope.launch {
                                viewModel.huyDanhGia()
                            }
                        }
                    )
                } else {
                    DropdownMenuItem(
                        text = { Text("Chức năng đang được hoàn thiện") },
                        onClick = { }
                    )
                }
            }
        }
    }
}

@Composable
private fun ThanhDanhGia(
    viewModel: TrangChiTietNoiBanViewModel,
    coroutineScope: CoroutineScope,
    isEdit: MutableState<Boolean>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(corner = CornerSize(10.dp)))
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            for (index in 1..5) {
                IconToggleButton(
                    checked = viewModel.diemDanhGia >= index,
                    onCheckedChange = {
                        viewModel.diemDanhGia = index
                    }) {
                    if (viewModel.diemDanhGia >= index) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Đánh giá $index",
                            tint = Color.Yellow,
                            modifier = Modifier.size(22.dp)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.TwoTone.Star,
                            contentDescription = "Đánh giá $index",
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
            Button(onClick = {
                coroutineScope.launch {
                    if (isEdit.value) {
                        viewModel.capNhatDanhGia()
                        isEdit.value = !isEdit.value
                    } else {
                        viewModel.danhGia()
                    }
                }
            }) {
                Text(text = "Đánh giá", fontSize = 13.sp)
            }
            if (isEdit.value) {
                Button(onClick = {
                    isEdit.value = !isEdit.value
                }) {
                    Text(text = "Hủy", fontSize = 13.sp)
                }
            }
        }
        OutlinedTextField(
            value = viewModel.noiDung,
            onValueChange = { viewModel.noiDung = it },
            label = { Text(text = "Nội dung đánh giá", fontSize = 13.sp) },
            shape = RoundedCornerShape(5.dp),
            textStyle = TextStyle(fontSize = 13.sp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
private fun ThanhTieuDe(
    viewModel: TrangChiTietNoiBanViewModel,
    coroutineScope: CoroutineScope,
    context: Context
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (nguoiDung != null) Arrangement.SpaceBetween else Arrangement.Center,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(10.dp)
    ) {
        if (nguoiDung != null) {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                tint = Color.Transparent,
                contentDescription = "Chưa yêu thích",
            )
        }
        Text(
            text = viewModel.noiBan.value!!.ten,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            maxLines = 1,
        )
        if (nguoiDung != null) {
            IconYeuThich(viewModel, coroutineScope, context)
        }
    }
}


@Composable
private fun IconYeuThich(
    viewModel: TrangChiTietNoiBanViewModel,
    coroutineScope: CoroutineScope,
    context: Context
) {
    IconToggleButton(
        checked = viewModel.yeuThich ?: false,
        onCheckedChange = { isChecked ->
            coroutineScope.launch {
                val kq: Boolean
                var thongBao = "Đã yêu thích "
                if (isChecked) {
                    kq = viewModel.yeuThich(viewModel.noiBan.value!!.id)
                } else {
                    kq = viewModel.huyYeuThich(viewModel.noiBan.value!!.id)
                    thongBao = "Đã hủy yêu thích "
                }
                if (kq) {
                    Toast.makeText(
                        context,
                        "$thongBao ${viewModel.noiBan.value!!.ten}.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }, modifier = Modifier.size(20.dp)
    ) {
        if (viewModel.yeuThich == true) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Đã yêu thích",
                tint = Color(255, 105, 180)
            )
        } else {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = "Chưa yêu thích",
            )
        }
    }
}

@Composable
private fun ThanhThongKeTuongTac(viewModel: TrangChiTietNoiBanViewModel) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(corner = CornerSize(10.dp)))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(10.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = viewModel.noiBan.value!!.luot_xem.toString(),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = "Lượt xem", fontSize = 13.sp)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = viewModel.noiBan.value!!.luot_danh_gia.toString(),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = "Lượt đánh giá", fontSize = 13.sp)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = viewModel.noiBan.value!!.diem_danh_gia.toString(),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = "Điểm trung bình", fontSize = 13.sp)
        }
    }
}

@Composable
private fun ThanhMoTa(
    viewModel: TrangChiTietNoiBanViewModel,
    maxLineMT: MutableIntState,
    isExpandableMT: MutableState<Boolean>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(corner = CornerSize(10.dp)))
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Text(
            text = "Mô tả",
            fontSize = 14.sp,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    shape = RoundedCornerShape(
                        topStart = 10.dp,
                        topEnd = 10.dp
                    )
                )
                .background(Color(30, 144, 255))
                .padding(vertical = 5.dp, horizontal = 10.dp)
        )
        Text(
            text = viewModel.noiBan.value!!.mo_ta ?: "Chưa có thông tin",
            fontSize = 13.sp,
            maxLines = maxLineMT.intValue,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { result ->
                Log.d("Text layout", result.didOverflowHeight.toString())
                isExpandableMT.value = result.didOverflowHeight || result.lineCount > 4
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )
        if (isExpandableMT.value) {
            TextButton(
                onClick = {
                    maxLineMT.intValue = if (maxLineMT.intValue == 4) 1000 else 4
                }, shape = RoundedCornerShape(
                    bottomStart = 10.dp,
                    bottomEnd = 10.dp
                ), modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = if (maxLineMT.intValue == 4) "Xem thêm" else "Thu gọn")
                }
            }
        }
    }
}

@Composable
private fun ThanhDiaChi(viewModel: TrangChiTietNoiBanViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(corner = CornerSize(10.dp)))
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Text(
            text = "Địa chỉ",
            fontSize = 14.sp,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    shape = RoundedCornerShape(
                        topStart = 10.dp,
                        topEnd = 10.dp
                    )
                )
                .background(Color(30, 144, 255))
                .padding(vertical = 5.dp, horizontal = 10.dp)
        )
        Text(
            text = viewModel.noiBan.value!!.dia_chi.toString(),
            fontSize = 13.sp,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )
    }
}
