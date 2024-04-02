package com.example.appcsn.core.domain.repository

import com.example.appcsn.core.data.PhuongXa
import com.example.appcsn.core.domain.api.PhuongXaAPI

class PhuongXaRepository(
    private val api: PhuongXaAPI
) {
    suspend fun docTheoID(id: Int): Result<List<PhuongXa>> {
        val kq = api.docDanhSach(id)
        return if (kq.body() == null) {
            Result.failure(Throwable(message = "Đọc dữ liệu thất bại"))
        } else {
            Result.success(kq.body()!!)
        }
    }
}
