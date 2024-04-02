package com.example.appcsn.core.ui.screenstate

import com.example.appcsn.features.dacsan.data.DacSan

data class ScreenStateDacSan(
    val isLoading: Boolean = false,
    val ds: List<DacSan> = emptyList(),
    val errorMessage: String? = null,
    val isEnd: Boolean = false,
    val pageIndex: Int = 0,
)
