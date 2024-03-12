package com.example.appcsn.ui.screenstate

import com.example.appcsn.data.model.dacsan.DacSan

data class ScreenStateDacSan(
    val isLoading: Boolean = false,
    val ds: List<DacSan> = emptyList(),
    val errorMessage: String? = null,
    val isEnd: Boolean = false,
    val pageIndex: Int = 0,
)
