package com.example.appcsn.core.ui.screenstate

import com.example.appcsn.features.noiban.data.NoiBan

data class ScreenStateNoiBan(
    val isLoading: Boolean = false,
    val ds: List<NoiBan> = emptyList(),
    val errorMessage: String? = null,
    val isEnd: Boolean = false,
    val pageIndex: Int = 0,
)