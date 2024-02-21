package com.example.appcsn.repository

import com.example.appcsn.HttpHelper
import com.example.appcsn.models.DacSan

class DSRepository {
    suspend fun getData(pageIndex: Int, pageSize: Int): Result<List<DacSan>> {
        val dacSanAPI: DacSan.doc =
            HttpHelper.DacSanAPI.getInstance().create(DacSan.doc::class.java)
        return if (dacSanAPI.docTheoTrang(pageSize, pageIndex).body() == null) {
            Result.failure(Throwable(message = "Đọc dữ liệu thất bại"))
        } else {
            Result.success(dacSanAPI.docTheoTrang(pageIndex, pageSize).body()!!)
        }
    }
}
