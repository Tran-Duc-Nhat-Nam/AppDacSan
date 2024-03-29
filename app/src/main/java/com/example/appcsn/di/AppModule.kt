package com.example.appcsn.di

import android.app.Application
import com.example.appcsn.data.remote.DacSanAPI
import com.example.appcsn.data.remote.HinhAnhAPI
import com.example.appcsn.data.remote.MuaDacSanAPI
import com.example.appcsn.data.remote.NguoiDungAPI
import com.example.appcsn.data.remote.NguyenLieuAPI
import com.example.appcsn.data.remote.NoiBanAPI
import com.example.appcsn.data.remote.PhuongXaAPI
import com.example.appcsn.data.remote.QuanHuyenAPI
import com.example.appcsn.data.remote.TinhThanhAPI
import com.example.appcsn.data.remote.VungMienAPI
import com.example.appcsn.data.repository.DacSanRepository
import com.example.appcsn.data.repository.HinhAnhRepository
import com.example.appcsn.data.repository.MuaDacSanRepository
import com.example.appcsn.data.repository.NguoiDungRepository
import com.example.appcsn.data.repository.NguyenLieuRepository
import com.example.appcsn.data.repository.NoiBanRepository
import com.example.appcsn.data.repository.PhuongXaRepository
import com.example.appcsn.data.repository.QuanHuyenRepository
import com.example.appcsn.data.repository.TinhThanhRepository
import com.example.appcsn.data.repository.VungMienRepository
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
//        return Retrofit.Builder().baseUrl("https://dacsanimage-b5os5eg63q-de.a.run.app/")
        return Retrofit.Builder().baseUrl("http://192.168.1.50:8080/")
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                        .create()
                )
            )
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
