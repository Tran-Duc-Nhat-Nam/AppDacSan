package com.example.appcsn.features.noiban.ui.state

import com.example.appcsn.features.noiban.data.NoiBan

data class NoiBanScreenState(
    val isLoading: Boolean = false,
    val ds: List<NoiBan> = emptyList(),
    val errorMessage: String? = null,
    val isEnd: Boolean = false,
    val pageIndex: Int = 0,
)