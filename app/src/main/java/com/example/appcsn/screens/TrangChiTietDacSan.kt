package com.example.appcsn.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.appcsn.HttpHelper
import com.example.appcsn.models.DacSan
import kotlinx.coroutines.runBlocking

@Composable
fun TrangChiTietDacSan(
    idDacSan: Int,
) {
    var dacSan: DacSan? = null
    runBlocking {
        val apiDacSan =
            HttpHelper.DacSanAPI.getInstance().create(DacSan.docTheoID::class.java)

        val kq = apiDacSan.doc(idDacSan)

        if (kq.body() != null) {
            dacSan = kq.body()!!
        }
    }
    Column(
        Modifier
            .fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize())
        {
            Text(text = dacSan!!.ten)
        }
    }
}