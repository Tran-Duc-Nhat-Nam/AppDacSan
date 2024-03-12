package com.example.appcsn.data.remote

import com.example.appcsn.data.model.dacsan.DacSan
import com.example.appcsn.data.model.dacsan.LuotDanhGiaDacSan
import com.example.appcsn.data.model.dacsan.TuKhoaTimKiem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DacSanAPI {
    @GET("/dacsan")
    suspend fun timKiem(): Response<List<DacSan>>

    @GET("/dacsan/size={size}/index={index}")
    suspend fun timKiem(
        @Path("size") size: Int,
        @Path("index") index: Int
    ): Response<List<DacSan>>

    @GET("/dacsan/{id}")
    suspend fun timKiem(@Path("id") id: Int): Response<DacSan>

    @GET("/dacsan/{id}/xem")
    suspend fun xem(
        @Path("id") id: Int,
        @Path("idNguoiDung") idNguoiDung: String
    ): Response<DacSan>

    @GET("/dacsan/ten={ten}/size={size}/index={index}")
    suspend fun timKiem(
        @Path("ten") ten: String,
        @Path("size") size: Int,
        @Path("index") index: Int
    ): Response<List<DacSan>>

    @POST("/dacsan/theovungmien/ten={ten}/size={size}/index={index}")
    suspend fun timKiemTheoVungMien(
        @Path("ten") ten: String,
        @Path("size") size: Int,
        @Path("index") index: Int,
        @Body tuKhoaTimKiem: TuKhoaTimKiem
    ): Response<List<DacSan>>

    @POST("/dacsan/theomua/ten={ten}/size={size}/index={index}")
    suspend fun timKiemTheoMua(
        @Path("ten") ten: String,
        @Path("size") size: Int,
        @Path("index") index: Int,
        @Body tuKhoaTimKiem: TuKhoaTimKiem
    ): Response<List<DacSan>>

    @POST("/dacsan/theovungmien/theomua/ten={ten}/size={size}/index={index}")
    suspend fun timKiemTheoMuaVungMien(
        @Path("ten") ten: String,
        @Path("size") size: Int,
        @Path("index") index: Int,
        @Body tuKhoaTimKiem: TuKhoaTimKiem
    ): Response<List<DacSan>>

    @POST("/dacsan/theonguyenlieu/ten={ten}/size={size}/index={index}")
    suspend fun timKiemTheoNguyenLieu(
        @Path("ten") ten: String,
        @Path("size") size: Int,
        @Path("index") index: Int,
        @Body tuKhoaTimKiem: TuKhoaTimKiem
    ): Response<List<DacSan>>

    @POST("/dacsan/theonguyenlieu/theovungmien/ten={ten}/size={size}/index={index}")
    suspend fun timKiemTheoNguyenLieuVungMien(
        @Path("ten") ten: String,
        @Path("size") size: Int,
        @Path("index") index: Int,
        @Body tuKhoaTimKiem: TuKhoaTimKiem
    ): Response<List<DacSan>>

    @POST("/dacsan/theonguyenlieu/theomua/ten={ten}/size={size}/index={index}")
    suspend fun timKiemTheoNguyenLieuMua(
        @Path("ten") ten: String,
        @Path("size") size: Int,
        @Path("index") index: Int,
        @Body tuKhoaTimKiem: TuKhoaTimKiem
    ): Response<List<DacSan>>

    @POST("/dacsan/theodieukien/ten={ten}/size={size}/index={index}")
    suspend fun timKiemTheoDieuKien(
        @Path("ten") ten: String,
        @Path("size") size: Int,
        @Path("index") index: Int,
        @Body tuKhoaTimKiem: TuKhoaTimKiem
    ): Response<List<DacSan>>

    @GET("/dacsan/mota={mota}")
    suspend fun docTheoMoTa(@Path("mota") moTa: String): Response<List<DacSan>>

    @GET("/dacsan/cachchebien={cachchebien}")
    suspend fun docTheoCachCheBien(@Path("cachchebien") cachCheBien: String): Response<List<DacSan>>

    @GET("/yeuthich/dacsan={idDacSan}/nguoidung={idNguoiDung}")
    suspend fun checkLike(
        @Path("idDacSan") idDacSan: Int,
        @Path("idNguoiDung") idNguoiDung: String
    ): Response<Boolean>

    @POST("/yeuthich/dacsan={idDacSan}/nguoidung={idNguoiDung}")
    suspend fun like(
        @Path("idDacSan") idDacSan: Int,
        @Path("idNguoiDung") idNguoiDung: String
    ): Response<Boolean>

    @DELETE("/yeuthich/dacsan={idDacSan}/nguoidung={idNguoiDung}")
    suspend fun unlike(
        @Path("idDacSan") idDacSan: Int,
        @Path("idNguoiDung") idNguoiDung: String
    ): Response<Boolean>

    @GET("/danhgia/dacsan={idDacSan}/nguoidung={idNguoiDung}")
    suspend fun docDanhGia(
        @Path("idDacSan") idDacSan: Int,
        @Path("idNguoiDung") idNguoiDung: String
    ): Response<LuotDanhGiaDacSan>

    @POST("/danhgia/dacsan={id}")
    suspend fun danhGia(
        @Path("id") id: Int,
        @Body luotDanhGia: LuotDanhGiaDacSan
    ): Response<Boolean>
}
