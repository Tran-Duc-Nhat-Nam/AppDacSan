package com.example.appcsn.ui

import com.example.appcsn.data.model.dacsan.DacSan

data class ScreenState(
    val isLoading: Boolean = false,
    val ds: List<DacSan> = emptyList(),
    val errorMessage: String? = null,
    val isEnd: Boolean = false,
    val pageIndex: Int = 0,
)
