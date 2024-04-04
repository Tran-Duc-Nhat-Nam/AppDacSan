package com.example.appcsn.features.dacsan.ui.view

import android.content.Context
import android.icu.text.DecimalFormat
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.AssistChip
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.appcsn.R
import com.example.appcsn.core.ui.view.TrangBaoLoi
import com.example.appcsn.core.ui.viewmodel.BaseViewModel.Companion.dsNavItem
import com.example.appcsn.core.ui.viewmodel.BaseViewModel.Companion.kiemTraNguoiDung
import com.example.appcsn.core.ui.viewmodel.BaseViewModel.Companion.nguoiDung
import com.example.appcsn.core.ui.widget.CircleProgressIndicator
import com.example.appcsn.features.dacsan.data.LuotDanhGiaDacSan
import com.example.appcsn.features.dacsan.data.TuKhoaTimKiem
import com.example.appcsn.features.dacsan.ui.nav.FoodGraph
import com.example.appcsn.features.dacsan.ui.viewmodel.TrangChiTietDacSanViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.generated.destinations.TrangTimKiemDacSanDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Destination<FoodGraph>()
@Composable
fun TrangChiTietDacSan(
    navigator: DestinationsNavigator,
    viewModel: TrangChiTietDacSanViewModel,
    id: Int,
    navID: Int,
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val pagerState = rememberPagerState {
        viewModel.dsHinhAnh.size
    }
    val selectedImage = remember {
        mutableIntStateOf(-1)
    }
    val maxLineMT = remember {
        mutableIntStateOf(4)
    }
    val isExpandableMT = remember {
        mutableStateOf(false)
    }
    val maxLineCCB = remember {
        mutableIntStateOf(4)
    }
    val isExpandableCCB = remember {
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
        viewModel.dacSan.value = null
        dsNavItem[navID].backStack.removeLast()
        navigator.navigate(dsNavItem[navID].backStack.last())
    }

    if (viewModel.loading.value) {
        CircleProgressIndicator()
    } else if (viewModel.dacSan.value == null) {
        TrangBaoLoi("Không tìm được thông tin chi tiết của đặc sản")
    } else {
        Column {
            ThanhTieuDe(viewModel, coroutineScope, context)
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp)
            ) {
                item {
                    AsyncImage(
                        contentScale = ContentScale.Crop,
                        model = viewModel.dacSan.value!!.hinh_dai_dien.url,
                        contentDescription = viewModel.dacSan.value!!.ten,
                        error = painterResource(id = R.drawable.image_not_found_512),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp)
                            .clip(shape = RoundedCornerShape(10.dp))
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    DanhSachHinhAnh(viewModel, pagerState, selectedImage)
                    ThongKeTuongTac(viewModel)
                    Spacer(modifier = Modifier.height(15.dp))
                    ThanhMoTa(viewModel, maxLineMT, isExpandableMT)
                    Spacer(modifier = Modifier.height(15.dp))
                    ThanhCachCheBien(viewModel, maxLineCCB, isExpandableCCB)
                    Spacer(modifier = Modifier.height(15.dp))
                    ThanhVungMien(viewModel, navigator)
                    Spacer(modifier = Modifier.height(15.dp))
                    ThanhMuaDacSan(viewModel, navigator)
                    Spacer(modifier = Modifier.height(15.dp))
                    ThanhNguyenLieu(viewModel, navigator)
                    Spacer(modifier = Modifier.height(15.dp))
                    KhuVucDanhGia(viewModel, coroutineScope, isEdit, isExpandableDG)
                }
            }
        }
    }
}

@Composable
private fun KhuVucDanhGia(
    viewModel: TrangChiTietDacSanViewModel,
    coroutineScope: CoroutineScope,
    isEdit: MutableState<Boolean>,
    isExpandableDG: MutableState<Boolean>
) {
    if (nguoiDung != null || isEdit.value) {
        if (viewModel.luotDanhGiaDacSan == null || isEdit.value) {
            ThanhDanhGia(viewModel, coroutineScope, isEdit)
        } else {
            DanhGia(
                viewModel,
                coroutineScope,
                viewModel.luotDanhGiaDacSan!!,
                nguoiDung!!.ten,
                isEdit
            )
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
    DanhSachDanhGia(coroutineScope, viewModel, isExpandableDG, isEdit)
    Spacer(modifier = Modifier.height(15.dp))
}

@Composable
private fun ThanhTieuDe(
    viewModel: TrangChiTietDacSanViewModel,
    coroutineScope: CoroutineScope,
    context: Context
) {
    Surface(
        shadowElevation = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
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
                text = viewModel.dacSan.value!!.ten,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                maxLines = 1,
            )
            if (nguoiDung != null) {
                IconYeuThich(
                    viewModel,
                    coroutineScope,
                    context
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun ThanhVungMien(
    viewModel: TrangChiTietDacSanViewModel,
    navigator: DestinationsNavigator
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(corner = CornerSize(10.dp)))
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Text(
            text = "Vùng miền",
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
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(10.dp)
        ) {
            viewModel.dacSan.value!!.vung_mien.forEach { vungMien ->
                AssistChip(
                    onClick = {
                        navigator.navigate(
                            TrangTimKiemDacSanDestination(
                                ten = "",
                                tuKhoa = TuKhoaTimKiem(
                                    dsVungMien = mutableListOf(
                                        vungMien.id
                                    )
                                )
                            )
                        )
                    },
                    shape = RoundedCornerShape(25.dp),
                    label = { Text(text = vungMien.ten, fontSize = 13.sp) })
            }
        }
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun ThanhMuaDacSan(
    viewModel: TrangChiTietDacSanViewModel,
    navigator: DestinationsNavigator
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(corner = CornerSize(10.dp)))
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Text(
            text = "Mùa",
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
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(10.dp)
        ) {
            viewModel.dacSan.value!!.mua_dac_san.forEach { mua ->
                AssistChip(
                    onClick = {
                        navigator.navigate(
                            TrangTimKiemDacSanDestination(
                                ten = "",
                                tuKhoa = TuKhoaTimKiem(
                                    dsMuaDacSan = mutableListOf(
                                        mua.id
                                    )
                                )
                            )
                        )
                    },
                    shape = RoundedCornerShape(25.dp),
                    label = { Text(text = mua.ten, fontSize = 13.sp) })
            }
        }
    }
}

@Composable
private fun ThanhNguyenLieu(
    viewModel: TrangChiTietDacSanViewModel,
    navigator: DestinationsNavigator
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(corner = CornerSize(10.dp)))
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Text(
            text = "Nguyên liệu",
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
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            viewModel.dacSan.value!!.thanh_phan.forEach { thanhPhan ->
                Row {
                    Text(
                        text = "- ${thanhPhan.so_luong} ${thanhPhan.don_vi_tinh} ",
                        fontSize = 13.sp,
                    )
                    Text(
                        text = thanhPhan.nguyen_lieu.ten,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable {
                            dsNavItem[0].backStack.add(
                                TrangTimKiemDacSanDestination(
                                    ten = "",
                                    tuKhoa = TuKhoaTimKiem(
                                        dsNguyenLieu = mutableListOf(
                                            thanhPhan.nguyen_lieu.id
                                        )
                                    )
                                )
                            )
                            navigator.navigate(
                                dsNavItem[0].backStack.last()
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ThanhDanhGia(
    viewModel: TrangChiTietDacSanViewModel,
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
private fun IconYeuThich(
    viewModel: TrangChiTietDacSanViewModel,
    coroutineScope: CoroutineScope,
    context: Context
) {
    IconToggleButton(
        checked = viewModel.yeuThich ?: false,
        onCheckedChange = { isChecked ->
            yeuThich(coroutineScope, isChecked, viewModel, context)
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

private fun yeuThich(
    coroutineScope: CoroutineScope,
    isChecked: Boolean,
    viewModel: TrangChiTietDacSanViewModel,
    context: Context
) {
    if (kiemTraNguoiDung(nguoiDung = nguoiDung)) {
        coroutineScope.launch {
            if (isChecked) {
                val kq =
                    viewModel.yeuThich(viewModel.dacSan.value!!.id)
                if (kq) {
                    Toast.makeText(
                        context,
                        "Đã yêu thích ${viewModel.dacSan.value!!.ten}.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                val kq =
                    viewModel.huyYeuThich(viewModel.dacSan.value!!.id)
                if (kq) {
                    Toast.makeText(
                        context,
                        "Đã hủy yêu thích ${viewModel.dacSan.value!!.ten}.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    } else {
        Toast.makeText(
            context,
            "Vui lòng đăng nhập để sử dụng tính năng này.",
            Toast.LENGTH_SHORT
        ).show()
    }
}

@Composable
private fun ThanhCachCheBien(
    viewModel: TrangChiTietDacSanViewModel,
    maxLineCCB: MutableIntState,
    isExpandableCCB: MutableState<Boolean>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(corner = CornerSize(10.dp)))
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Text(
            text = "Cách chế biến",
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
            text = viewModel.dacSan.value!!.cach_che_bien ?: "Chưa có thông tin",
            fontSize = 13.sp,
            maxLines = maxLineCCB.intValue,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { result ->
                isExpandableCCB.value =
                    result.didOverflowHeight || result.lineCount > 4
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )
        if (isExpandableCCB.value) {
            TextButton(
                onClick = {
                    maxLineCCB.intValue = if (maxLineCCB.intValue == 4) 1000 else 4
                }, shape = RoundedCornerShape(
                    bottomStart = 10.dp,
                    bottomEnd = 10.dp
                ), modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = if (maxLineCCB.intValue == 4) "Xem thêm" else "Thu gọn")
                }
            }
        }
    }
}

@Composable
private fun ThanhMoTa(
    viewModel: TrangChiTietDacSanViewModel,
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
            text = viewModel.dacSan.value!!.mo_ta ?: "Chưa có thông tin",
            fontSize = 13.sp,
            maxLines = maxLineMT.intValue,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { result ->
                isExpandableMT.value =
                    result.didOverflowHeight || result.lineCount > 4
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
private fun ThongKeTuongTac(viewModel: TrangChiTietDacSanViewModel) {
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
                text = viewModel.dacSan.value!!.luot_xem.toString(),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = "Lượt xem", fontSize = 13.sp)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = viewModel.dacSan.value!!.luot_danh_gia.toString(),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = "Lượt đánh giá", fontSize = 13.sp)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = DecimalFormat("#.##").format(viewModel.dacSan.value!!.diem_danh_gia),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = "Điểm trung bình", fontSize = 13.sp)
        }
    }
}

@Composable
private fun DanhSachHinhAnh(
    viewModel: TrangChiTietDacSanViewModel,
    pagerState: PagerState,
    selectedImage: MutableIntState
) {
    if (viewModel.dsHinhAnh.isNotEmpty()) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(8.dp),
            pageSpacing = 6.dp,
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) { index ->
            AsyncImage(
                contentScale = ContentScale.Crop,
                model = viewModel.dsHinhAnh[index].url,
                contentDescription = viewModel.dsHinhAnh[index].moTa,
                error = painterResource(id = R.drawable.image_not_found_128),
                modifier = Modifier
                    .size(75.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .clickable {
                        selectedImage.intValue = index
                    }
            )
        }
        if (selectedImage.intValue >= 0) {
            Dialog(onDismissRequest = { selectedImage.intValue = -1 }) {
                AsyncImage(
                    contentScale = ContentScale.Crop,
                    model = viewModel.dsHinhAnh[selectedImage.intValue].url,
                    contentDescription = viewModel.dsHinhAnh[selectedImage.intValue].moTa,
                    error = painterResource(id = R.drawable.image_not_found_512),
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1F)
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}


@Composable
private fun DanhSachDanhGia(
    coroutineScope: CoroutineScope,
    viewModel: TrangChiTietDacSanViewModel,
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
        if (viewModel.dsDanhGiaDacSan.isNotEmpty()) {
            viewModel.dsDanhGiaDacSan.forEach { cmt ->
                if (cmt.key.id_nguoi_dung != (nguoiDung?.id ?: "null")) {
                    Spacer(modifier = Modifier.height(10.dp))
                    DanhGia(viewModel, coroutineScope, cmt.key, cmt.value, isEdit)
                }
            }
        } else {
            Text(text = "Chưa có đánh giá nào khác", Modifier.padding(vertical = 10.dp))
        }
    }
}

@Composable
private fun DanhGia(
    viewModel: TrangChiTietDacSanViewModel,
    coroutineScope: CoroutineScope,
    luotDanhGia: LuotDanhGiaDacSan,
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