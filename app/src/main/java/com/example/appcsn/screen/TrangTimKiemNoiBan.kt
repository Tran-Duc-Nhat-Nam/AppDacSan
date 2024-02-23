package com.example.appcsn.screen

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
import com.example.appcsn.data.model.NoiBan
import com.example.appcsn.screen.destinations.TrangChiTietNoiBanDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun TrangTimKiemNoiBan(
    navigator: DestinationsNavigator,
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
                    navigator.navigate(TrangChiTietNoiBanDestination(noiBan = it))
                }) {
                    Text(text = it.ten)
                }
            }
        }
    }
}
