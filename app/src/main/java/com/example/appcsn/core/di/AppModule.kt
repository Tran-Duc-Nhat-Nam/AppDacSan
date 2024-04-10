package com.example.appcsn.core.di

import android.app.Application
import com.example.appcsn.core.domain.api.BasicAuthInterceptor
import com.example.appcsn.core.domain.api.HinhAnhAPI
import com.example.appcsn.core.domain.api.PhuongXaAPI
import com.example.appcsn.core.domain.api.QuanHuyenAPI
import com.example.appcsn.core.domain.api.TinhThanhAPI
import com.example.appcsn.core.domain.repository.HinhAnhRepository
import com.example.appcsn.core.domain.repository.PhuongXaRepository
import com.example.appcsn.core.domain.repository.QuanHuyenRepository
import com.example.appcsn.core.domain.repository.TinhThanhRepository
import com.example.appcsn.features.dacsan.domain.api.DacSanAPI
import com.example.appcsn.features.dacsan.domain.api.DanhGiaDacSanAPI
import com.example.appcsn.features.dacsan.domain.api.MuaDacSanAPI
import com.example.appcsn.features.dacsan.domain.api.NguyenLieuAPI
import com.example.appcsn.features.dacsan.domain.api.VungMienAPI
import com.example.appcsn.features.dacsan.domain.api.XemDacSanAPI
import com.example.appcsn.features.dacsan.domain.api.YeuThichDacSanAPI
import com.example.appcsn.features.dacsan.domain.repository.DacSanRepository
import com.example.appcsn.features.dacsan.domain.repository.DanhGiaDacSanRepository
import com.example.appcsn.features.dacsan.domain.repository.MuaDacSanRepository
import com.example.appcsn.features.dacsan.domain.repository.NguyenLieuRepository
import com.example.appcsn.features.dacsan.domain.repository.VungMienRepository
import com.example.appcsn.features.dacsan.domain.repository.XemDacSanRepository
import com.example.appcsn.features.dacsan.domain.repository.YeuThichDacSanRepository
import com.example.appcsn.features.nguoidung.domain.api.NguoiDungAPI
import com.example.appcsn.features.nguoidung.domain.repository.NguoiDungRepository
import com.example.appcsn.features.noiban.domain.api.DanhGiaNoiBanAPI
import com.example.appcsn.features.noiban.domain.api.NoiBanAPI
import com.example.appcsn.features.noiban.domain.api.XemNoiBanAPI
import com.example.appcsn.features.noiban.domain.api.YeuThichNoiBanAPI
import com.example.appcsn.features.noiban.domain.repository.DanhGiaNoiBanRepository
import com.example.appcsn.features.noiban.domain.repository.NoiBanRepository
import com.example.appcsn.features.noiban.domain.repository.XemNoiBanRepository
import com.example.appcsn.features.noiban.domain.repository.YeuThichNoiBanRepository
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(BasicAuthInterceptor("admindacsan", "Vinafood2024"))
            .build()
        val gson = GsonConverterFactory.create(
            GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                .create()
        )
        return Retrofit.Builder()
            .baseUrl("https://dacsanimage-b5os5eg63q-de.a.run.app/")
//        return Retrofit.Builder().baseUrl("http://192.168.1.50:8080/")
            .client(client)
            .addConverterFactory(gson)
            .build()
    }

    @Provides
    @Singleton
    fun provideDacSanAPI(retrofit: Retrofit): DacSanAPI {
        return retrofit.create(DacSanAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideDacSanRepository(api: DacSanAPI): DacSanRepository {
        return DacSanRepository(api)
    }

    @Provides
    @Singleton
    fun provideDanhGiaDacSanAPI(retrofit: Retrofit): DanhGiaDacSanAPI {
        return retrofit.create(DanhGiaDacSanAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideDanhGiaRepository(api: DanhGiaDacSanAPI): DanhGiaDacSanRepository {
        return DanhGiaDacSanRepository(api)
    }

    @Provides
    @Singleton
    fun provideYeuThichDacSanAPI(retrofit: Retrofit): YeuThichDacSanAPI {
        return retrofit.create(YeuThichDacSanAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideYeuThichDacSanRepository(api: YeuThichDacSanAPI): YeuThichDacSanRepository {
        return YeuThichDacSanRepository(api)
    }

    @Provides
    @Singleton
    fun provideXemDacSanAPI(retrofit: Retrofit): XemDacSanAPI {
        return retrofit.create(XemDacSanAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideXemDacSanRepository(api: XemDacSanAPI): XemDacSanRepository {
        return XemDacSanRepository(api)
    }

    @Provides
    @Singleton
    fun provideVungMienAPI(retrofit: Retrofit): VungMienAPI {
        return retrofit.create(VungMienAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideVungMienRepository(api: VungMienAPI): VungMienRepository {
        return VungMienRepository(api)
    }

    @Provides
    @Singleton
    fun provideMuaDacSanAPI(retrofit: Retrofit): MuaDacSanAPI {
        return retrofit.create(MuaDacSanAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideMuaDacSanRepository(api: MuaDacSanAPI): MuaDacSanRepository {
        return MuaDacSanRepository(api)
    }

    @Provides
    @Singleton
    fun provideNguyenLieuAPI(retrofit: Retrofit): NguyenLieuAPI {
        return retrofit.create(NguyenLieuAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideNguyenLieuRepository(api: NguyenLieuAPI): NguyenLieuRepository {
        return NguyenLieuRepository(api)
    }

    @Provides
    @Singleton
    fun provideHinhAnhAPI(retrofit: Retrofit): HinhAnhAPI {
        return retrofit.create(HinhAnhAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideHinhAnhRepository(api: HinhAnhAPI): HinhAnhRepository {
        return HinhAnhRepository(api)
    }

    @Provides
    @Singleton
    fun provideNoiBanAPI(retrofit: Retrofit): NoiBanAPI {
        return retrofit.create(NoiBanAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideNoiBanRepository(api: NoiBanAPI): NoiBanRepository {
        return NoiBanRepository(api)
    }

    @Provides
    @Singleton
    fun provideDanhGiaNoiBanAPI(retrofit: Retrofit): DanhGiaNoiBanAPI {
        return retrofit.create(DanhGiaNoiBanAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideDanhGiaNoiBanRepository(api: DanhGiaNoiBanAPI): DanhGiaNoiBanRepository {
        return DanhGiaNoiBanRepository(api)
    }

    @Provides
    @Singleton
    fun provideYeuThichNoiBanAPI(retrofit: Retrofit): YeuThichNoiBanAPI {
        return retrofit.create(YeuThichNoiBanAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideYeuThichNoiBanRepository(api: YeuThichNoiBanAPI): YeuThichNoiBanRepository {
        return YeuThichNoiBanRepository(api)
    }

    @Provides
    @Singleton
    fun provideXemNoiBanAPI(retrofit: Retrofit): XemNoiBanAPI {
        return retrofit.create(XemNoiBanAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideXemNoiBanRepository(api: XemNoiBanAPI): XemNoiBanRepository {
        return XemNoiBanRepository(api)
    }

    @Provides
    @Singleton
    fun provideTinhThanhAPI(retrofit: Retrofit): TinhThanhAPI {
        return retrofit.create(TinhThanhAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideTinhThanhRepository(api: TinhThanhAPI): TinhThanhRepository {
        return TinhThanhRepository(api)
    }

    @Provides
    @Singleton
    fun provideQuanHuyenAPI(retrofit: Retrofit): QuanHuyenAPI {
        return retrofit.create(QuanHuyenAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideQuanHuyenRepository(api: QuanHuyenAPI): QuanHuyenRepository {
        return QuanHuyenRepository(api)
    }

    @Provides
    @Singleton
    fun providePhuongXaAPI(retrofit: Retrofit): PhuongXaAPI {
        return retrofit.create(PhuongXaAPI::class.java)
    }

    @Provides
    @Singleton
    fun providePhuongXaRepository(api: PhuongXaAPI): PhuongXaRepository {
        return PhuongXaRepository(api)
    }

    @Provides
    @Singleton
    fun provideNguoiDungAPI(retrofit: Retrofit): NguoiDungAPI {
        return retrofit.create(NguoiDungAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideNguoiDungRepository(api: NguoiDungAPI): NguoiDungRepository {
        return NguoiDungRepository(api)
    }

    @Provides
    @Singleton
    fun initPlaceClient(app: Application): PlacesClient {
        Places.initializeWithNewPlacesApiEnabled(app, "MAPS_API_KEY")
        return Places.createClient(app)
    }
}
