package com.example.appcsn.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.appcsn.data.model.NoiBan
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun TrangChiTietNoiBan(noiBan: NoiBan) {
    Column(
        Modifier
            .fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize())
        {
            Text(text = noiBan.ten)
        }
    }
}
