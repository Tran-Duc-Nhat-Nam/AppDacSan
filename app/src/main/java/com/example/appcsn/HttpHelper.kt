package com.example.appcsn

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HttpHelper {
    object DacSanAPI {
        fun getInstance(): Retrofit {
//            return Retrofit.Builder().baseUrl("https://dacsanimage-b5os5eg63q-de.a.run.app/")
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
    }
}
