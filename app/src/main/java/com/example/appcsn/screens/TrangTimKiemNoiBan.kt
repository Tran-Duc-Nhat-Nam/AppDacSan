package com.example.appcsn.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.appcsn.models.NoiBan
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun TrangTimKiemNoiBan(
    navController: NavController,
    dsNoiBan: List<NoiBan>
) {
    Column(
        Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(items = dsNoiBan)
            {
                Button(onClick = {
                    navController.navigate("${Screen.TrangNoiBan.route}/${it.id}")
                }) {
                    Text(text = it.ten)
                }
            }
        }
    }
}