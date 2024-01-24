package com.example.appcsn.screens

sealed class Screen(val route: String) {
    data object TrangDacSan : Screen("trang_dac_san") {
        data object TrangChuDacSan : Screen("trang_chu_dac_san")
        data object TrangTimKiemDacSan : Screen("trang_tim_kiem_dac_san")
        data object TrangChiTietDacSan : Screen("trang_chi_tiet_dac_san")
    }

    data object TrangNoiBan : Screen("trang_noi_ban") {
        data object TrangChuNoiBan : Screen("trang_chu_noi_ban")
        data object TrangTimKiemNoiBan : Screen("trang_tim_kiem_noi_ban")
        data object TrangChiTietNoiBan : Screen("trang_chi_tiet_noi_ban")
    }

    data object TrangNguoiDung : Screen("trang_nguoi_Dung")
}