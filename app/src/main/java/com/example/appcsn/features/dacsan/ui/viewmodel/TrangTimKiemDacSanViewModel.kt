package com.example.appcsn.features.dacsan.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.appcsn.core.domain.repository.BasePaginationRepository
import com.example.appcsn.core.ui.viewmodel.BaseViewModel
import com.example.appcsn.features.dacsan.data.TuKhoaTimKiem
import com.example.appcsn.features.dacsan.domain.repository.DacSanRepository
import com.example.appcsn.features.dacsan.ui.state.DacSanScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrangTimKiemDacSanViewModel @Inject constructor(
    private val repository: DacSanRepository
) : BaseViewModel() {
    var state by mutableStateOf(DacSanScreenState())
    private val paginator = BasePaginationRepository(
        initKey = state.pageIndex,
        onLoading = {
            state = state.copy(isLoading = it)
        },
        onRequest = { nextPage: Int ->
            repository.timKiem(ten, tuKhoa, 2, nextPage)
        },
        getNextKey = {
            state.pageIndex + 1
        },
        onError = {
            state = state.copy(errorMessage = it?.localizedMessage)
        },
        onSuccess = { items, newKey ->
            state = state.copy(
                itemList = state.itemList + items,
                pageIndex = newKey,
                isEnd = items.isEmpty()
            )
        }
    )
    var ten = ""
    var tuKhoa = TuKhoaTimKiem()

    fun loadNext() {
        viewModelScope.launch {
            paginator.loadNext()
        }
    }

    fun reset() {
        state = state.copy(pageIndex = 0, itemList = emptyList(), isEnd = false)
        paginator.reset()
        loadNext()
    }
}
