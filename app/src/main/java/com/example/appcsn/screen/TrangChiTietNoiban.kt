package com.example.appcsn.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcsn.data.model.NoiBan
import com.example.appcsn.ui.PageHeader
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun TrangChiTietNoiBan(noiBan: NoiBan) {
    var maxLineMT by remember {
        mutableIntStateOf(4)
    }
    var isExpandableMT by remember {
        mutableStateOf(false)
    }

    Column(
        Modifier
            .fillMaxSize()
    ) {
        PageHeader(text = noiBan.ten)
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Tên nơi bán: ${noiBan.ten}}",
            modifier = Modifier.padding(15.dp)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(corner = CornerSize(10.dp)))
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Text(
                text = "Mô tả",
                fontSize = 16.sp,
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
                    .padding(10.dp)
            )
            Text(
                text = noiBan.mo_ta ?: "Chưa có thông tin",
                fontSize = 16.sp,
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
        Text(
            text = "Địa chỉ: ${noiBan.dia_chi}",
            modifier = Modifier.padding(15.dp)
        )
    }
}
