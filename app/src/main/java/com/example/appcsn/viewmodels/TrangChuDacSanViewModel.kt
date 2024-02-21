package com.example.appcsn.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.appcsn.HttpHelper
import com.example.appcsn.models.DacSan
import com.example.appcsn.models.VungMien
import com.example.appcsn.repository.BaseRepository
import com.example.appcsn.repository.DSRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class TrangChuDacSanViewModel : BaseViewModel() {
    private val repository = DSRepository()
    var state by mutableStateOf(DSState())
    val paginator = BaseRepository(
        initKey = state.pageIndex,
        onLoading = {
            state = state.copy(isLoading = it)
        },
        onRequest = { nextPage: Int ->
            repository.getData(nextPage, 2)
        },
        getNextKey = {
            state.pageIndex + 1
        },
        onError = {
            state = state.copy(errorMessage = it?.localizedMessage)
        },
        onSuccess = { items, newKey ->
            state = state.copy(ds = state.ds + items, pageIndex = newKey, isEnd = items.isEmpty())
        }
    )
    val dsVungMien = mutableStateListOf<VungMien>()
    private val vungMienAPI: VungMien.docAPI =
        HttpHelper.DacSanAPI.getInstance().create(VungMien.docAPI::class.java)
    private val dacSanAPI: DacSan.doc =
        HttpHelper.DacSanAPI.getInstance().create(DacSan.doc::class.java)

    init {
        loadNext()
        viewModelScope.launch {
            docDuLieu()
        }
    }

    fun loadNext() {
        viewModelScope.launch {
            paginator.loadNext()
        }
    }

    suspend fun docDuLieu() {
        val kq = vungMienAPI.docDanhSach()

        if (kq.body() != null) {
            dsVungMien.addAll(kq.body()!!)
        }
    }

    suspend fun like(id: Int): Boolean {
        val kq: Response<String>

        try {
            kq = dacSanAPI.like(id, nguoiDung!!.id)
        } catch (_: Exception) {
            return false
        }

        return kq.body() == "true"
    }
}

data class DSState(
    val isLoading: Boolean = false,
    val ds: List<DacSan> = emptyList(),
    val errorMessage: String? = null,
    val isEnd: Boolean = false,
    val pageIndex: Int = 0,
)
