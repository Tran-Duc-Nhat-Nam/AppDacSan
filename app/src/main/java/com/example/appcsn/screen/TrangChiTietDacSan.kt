package com.example.appcsn.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material3.AssistChip
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.appcsn.R
import com.example.appcsn.data.model.DanhSachVungMien
import com.example.appcsn.data.model.dacsan.DacSan
import com.example.appcsn.screen.destinations.TrangTimKiemDacSanDestination
import com.example.appcsn.viewmodel.BaseViewModel
import com.example.appcsn.viewmodel.TrangChiTietDacSanViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Destination
@Composable
fun TrangChiTietDacSan(
    navigator: DestinationsNavigator,
    dacSan: DacSan,
) {
    val viewModel = hiltViewModel<TrangChiTietDacSanViewModel>()
    viewModel.dacSan = dacSan

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var maxLineMT by remember {
        mutableIntStateOf(4)
    }
    var isExpandableMT by remember {
        mutableStateOf(false)
    }
    var maxLineCCB by remember {
        mutableIntStateOf(4)
    }
    var isExpandableCCB by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(true) {
        viewModel.checkLike(dacSan.id)
        viewModel.docDanhGia()
    }

    Column {
        Surface(
            color = MaterialTheme.colorScheme.secondaryContainer,
            shadowElevation = 1.dp, modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    tint = Color.Transparent,
                    contentDescription = "Chưa yêu thích",
                )
                Text(
                    text = dacSan.ten,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    maxLines = 1,
                )
                IconToggleButton(
                    checked = viewModel.yeuThich ?: false,
                    onCheckedChange = { isChecked ->
                        if (BaseViewModel.nguoiDung != null) {
                            coroutineScope.launch {
                                if (isChecked) {
                                    val kq =
                                        viewModel.like(dacSan.id)
                                    if (kq) {
                                        Toast.makeText(
                                            context,
                                            "Đã yêu thích ${dacSan.ten}.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } else {
                                    val kq =
                                        viewModel.unlike(dacSan.id)
                                    if (kq) {
                                        Toast.makeText(
                                            context,
                                            "Đã hủy yêu thích ${dacSan.ten}.",
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
        }
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) {
            item {
                AsyncImage(
                    contentScale = ContentScale.Crop,
                    model = dacSan.hinh_dai_dien.url,
                    contentDescription = dacSan.ten,
                    error = painterResource(id = R.drawable.image_not_found_512),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp)
                        .clip(shape = RoundedCornerShape(corner = CornerSize(10.dp)))
                )
                Spacer(modifier = Modifier.height(20.dp))
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
                            text = dacSan.luot_xem.toString(),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = "Lượt xem", fontSize = 13.sp)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = dacSan.luot_danh_gia.toString(),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = "Lượt đánh giá", fontSize = 13.sp)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = dacSan.diem_danh_gia.toString(),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = "Điểm trung bình", fontSize = 13.sp)
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
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
                        text = dacSan.mo_ta ?: "Chưa có thông tin",
                        fontSize = 13.sp,
                        maxLines = maxLineMT,
                        overflow = TextOverflow.Ellipsis,
                        onTextLayout = { result ->
                            Log.d("Text layout", result.didOverflowHeight.toString())
                            isExpandableMT = result.didOverflowHeight || result.lineCount > 4
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    )
                    if (isExpandableMT) {
                        TextButton(
                            onClick = {
                                maxLineMT = if (maxLineMT == 4) 1000 else 4
                            }, shape = RoundedCornerShape(
                                bottomStart = 10.dp,
                                bottomEnd = 10.dp
                            ), modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(text = if (maxLineMT == 4) "Xem thêm" else "Thu gọn")
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
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
                        text = dacSan.cach_che_bien ?: "Chưa có thông tin",
                        fontSize = 13.sp,
                        maxLines = maxLineCCB,
                        overflow = TextOverflow.Ellipsis,
                        onTextLayout = { result ->
                            Log.d("Text layout", result.didOverflowHeight.toString())
                            isExpandableCCB = result.didOverflowHeight || result.lineCount > 4
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    )
                    if (isExpandableCCB) {
                        TextButton(
                            onClick = {
                                maxLineCCB = if (maxLineCCB == 4) 1000 else 4
                            }, shape = RoundedCornerShape(
                                bottomStart = 10.dp,
                                bottomEnd = 10.dp
                            ), modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(text = if (maxLineCCB == 4) "Xem thêm" else "Thu gọn")
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
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
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        dacSan.vung_mien.forEach { vungMien ->
                            AssistChip(
                                onClick = {
                                    navigator.navigate(
                                        TrangTimKiemDacSanDestination(
                                            ten = null,
                                            dsVungMien = DanhSachVungMien(listOf(vungMien))
                                        )
                                    )
                                },
                                shape = RoundedCornerShape(25.dp),
                                label = { Text(text = vungMien.ten, fontSize = 13.sp) })
                        }
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                if (BaseViewModel.nguoiDung != null) {
                    if (viewModel.luotDanhGiaDacSan == null) {
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
                    } else {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(corner = CornerSize(10.dp)))
                                .background(MaterialTheme.colorScheme.primaryContainer)
                                .padding(5.dp)
                        ) {
                            Column(modifier = Modifier.weight(1F)) {
                                Text(
                                    text = "Đánh giá của bạn",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.padding(5.dp)
                                )
                                Text(
                                    text = viewModel.luotDanhGiaDacSan!!.noi_dung!!,
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
                                    Text(text = viewModel.luotDanhGiaDacSan!!.diem_danh_gia.toString())
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
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
        }
    }
}
