package com.example.appcsn.core.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CircleProgressIndicator(
    size: Dp = 50.dp,
    strokeWidth: Dp = 5.dp,
    innerPadding: PaddingValues = PaddingValues(0.dp),
    outerPadding: PaddingValues = PaddingValues(0.dp),
    backgroundColor: Color = Color.Unspecified
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            strokeWidth = strokeWidth,
            modifier = Modifier
                .padding(10.dp)
                .size(size + 20.dp)
                .clip(CircleShape)
                .background(backgroundColor)
        )
    }
}
