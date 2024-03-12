package com.example.appcsn.ui.screenstate

import com.example.appcsn.data.model.noiban.NoiBan

data class ScreenStateNoiBan(
    val isLoading: Boolean = false,
    val ds: List<NoiBan> = emptyList(),
    val errorMessage: String? = null,
    val isEnd: Boolean = false,
    val pageIndex: Int = 0,
)