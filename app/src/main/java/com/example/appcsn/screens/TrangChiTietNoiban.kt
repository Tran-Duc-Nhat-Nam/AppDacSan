package com.example.appcsn.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.appcsn.HttpHelper
import com.example.appcsn.models.NoiBan
import kotlinx.coroutines.runBlocking

@Composable
fun TrangChiTietNoiBan(
    idNoiBan: Int,
) {
    var noiBan: NoiBan? = null
    runBlocking {
        val apiNoiBan =
            HttpHelper.DacSanAPI.getInstance().create(NoiBan.docTheoID::class.java)

        val kq = apiNoiBan.doc(idNoiBan)

        if (kq.body() != null) {
            noiBan = kq.body()!!
        }
    }
    Column(
        Modifier
            .fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize())
        {
            Text(text = noiBan!!.ten)
        }
    }
}