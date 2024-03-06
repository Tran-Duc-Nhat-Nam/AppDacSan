package com.example.appcsn.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.twotone.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.ceil

@Composable
fun StarBar(
    maxGrade: Int = 5,
    grade: Double = 0.0,
    size: Int = 15
) {
    for (index in 1..maxGrade) {
        if (ceil(grade) >= index) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color.Yellow,
                modifier = Modifier.size(size.dp)
            )
        } else {
            Icon(
                imageVector = Icons.TwoTone.Star,
                contentDescription = null,
                modifier = Modifier.size(size.dp)
            )
        }
    }
}
