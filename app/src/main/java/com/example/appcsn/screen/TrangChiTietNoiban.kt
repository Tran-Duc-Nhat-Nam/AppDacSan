package com.example.appcsn.screen

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
import androidx.compose.material3.Icon
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appcsn.data.model.noiban.LuotDanhGiaNoiBan
import com.example.appcsn.data.model.noiban.NoiBan
import com.example.appcsn.ui.navgraph.PlaceGraph
import com.example.appcsn.viewmodel.BaseViewModel
import com.example.appcsn.viewmodel.BaseViewModel.Companion.dsNavItem
import com.example.appcsn.viewmodel.BaseViewModel.Companion.nguoiDung
import com.example.appcsn.viewmodel.TrangChiTietNoiBanViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Destination<PlaceGraph>()
@Composable
fun TrangChiTietNoiBan(
    navigator: DestinationsNavigator,
    noiBan: NoiBan,
) {
    val viewModel = hiltViewModel<TrangChiTietNoiBanViewModel>()
    viewModel.noiBan = noiBan

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val maxLineMT = remember {
        mutableIntStateOf(4)
    }
    val isExpandableMT = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(true) {
        viewModel.checkLike(noiBan.id)
        viewModel.docDanhGia()
    }

    BackHandler {
        dsNavItem[1].backStack.removeLast()
        navigator.navigate(dsNavItem[1].backStack.last())
    }

    Column {
        Surface(
            shadowElevation = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            ThanhTieuDe(noiBan, viewModel, coroutineScope, context)
        }
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) {
            item {
                ThanhThongKeTuongTac(noiBan)
                Spacer(modifier = Modifier.height(15.dp))
                ThanhMoTa(noiBan, maxLineMT, isExpandableMT)
                Spacer(modifier = Modifier.height(15.dp))
                ThanhDiaChi(noiBan)
                Spacer(modifier = Modifier.height(15.dp))
                if (nguoiDung != null) {
                    if (viewModel.luotDanhGia == null) {
                        ThanhDanhGia(viewModel, coroutineScope)
                    } else {
                        DanhGia(viewModel.luotDanhGia!!)
                    }
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
        }
    }
}

@Composable
private fun DanhGia(luotDanhGia: LuotDanhGiaNoiBan) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(corner = CornerSize(10.dp)))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(5.dp)
    ) {
        Column(modifier = Modifier.weight(1F)) {
            Text(
                text = luotDanhGia.id_nguoi_dung,
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
                    modifier = Modifier.size(18.dp)
                )
            }
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Tùy chọn",
                modifier = Modifier
                    .size(36.dp)
                    .padding(5.dp)
            )
        }
    }
}

@Composable
private fun ThanhDanhGia(
    viewModel: TrangChiTietNoiBanViewModel,
    coroutineScope: CoroutineScope
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
                    viewModel.danhGia()
                }
            }) {
                Text(text = "Đánh giá", fontSize = 13.sp)
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
    noiBan: NoiBan,
    viewModel: TrangChiTietNoiBanViewModel,
    coroutineScope: CoroutineScope,
    context: Context
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(10.dp)
    ) {
        Icon(
            imageVector = Icons.Default.FavoriteBorder,
            tint = Color.Transparent,
            contentDescription = "Chưa yêu thích",
        )
        Text(
            text = noiBan.ten,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            maxLines = 1,
        )
        IconYeuThich(viewModel, coroutineScope, noiBan, context)
    }
}


@Composable
private fun IconYeuThich(
    viewModel: TrangChiTietNoiBanViewModel,
    coroutineScope: CoroutineScope,
    noiBan: NoiBan,
    context: Context
) {
    IconToggleButton(
        checked = viewModel.yeuThich ?: false,
        onCheckedChange = { isChecked ->
            if (BaseViewModel.nguoiDung != null) {
                coroutineScope.launch {
                    if (isChecked) {
                        val kq =
                            viewModel.like(noiBan.id)
                        if (kq) {
                            Toast.makeText(
                                context,
                                "Đã yêu thích ${noiBan.ten}.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        val kq =
                            viewModel.unlike(noiBan.id)
                        if (kq) {
                            Toast.makeText(
                                context,
                                "Đã hủy yêu thích ${noiBan.ten}.",
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
private fun ThanhThongKeTuongTac(noiBan: NoiBan) {
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
                text = noiBan.luot_xem.toString(),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = "Lượt xem", fontSize = 13.sp)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = noiBan.luot_danh_gia.toString(),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = "Lượt đánh giá", fontSize = 13.sp)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = noiBan.diem_danh_gia.toString(),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = "Điểm trung bình", fontSize = 13.sp)
        }
    }
}

@Composable
private fun ThanhMoTa(
    noiBan: NoiBan,
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
            text = noiBan.mo_ta ?: "Chưa có thông tin",
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
private fun ThanhDiaChi(noiBan: NoiBan) {
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
            text = noiBan.dia_chi.toString(),
            fontSize = 13.sp,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        )
    }
}
