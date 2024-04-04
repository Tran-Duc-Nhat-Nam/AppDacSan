package com.example.appcsn.features.dacsan.ui.state

import com.example.appcsn.features.dacsan.data.DacSan

data class DacSanScreenState(
    val isLoading: Boolean = false,
    val itemList: List<DacSan> = emptyList(),
    val errorMessage: String? = null,
    val isEnd: Boolean = false,
    val pageIndex: Int = 0,
)
