package com.example.appcsn.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.appcsn.HttpHelper
import com.example.appcsn.models.NguoiDung
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.runBlocking

@Destination
@Composable
fun TrangNguoiDung(
    idNguoiDung: Int,
) {
    var nguoiDung: NguoiDung? = null
    runBlocking {
        val apiDacSan =
            HttpHelper.DacSanAPI.getInstance().create(NguoiDung.docTheoID::class.java)

        val kq = apiDacSan.doc(idNguoiDung)

        if (kq.body() != null) {
            nguoiDung = kq.body()!!
        }
    }
    Column(
        Modifier
            .fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
        {
            Text(text = nguoiDung!!.ten)
        }
    }
}