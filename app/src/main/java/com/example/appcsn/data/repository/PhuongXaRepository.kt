package com.example.appcsn.data.repository

import com.example.appcsn.data.model.PhuongXa
import com.example.appcsn.data.remote.PhuongXaAPI

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
