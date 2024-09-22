package com.example.login.network

import com.example.login.api.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var retrofit: Retrofit? = null
    private const val BASE_URL = "http://10.0.2.2:8080/" // 실제 서버 URL

    private val instance: Retrofit
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }

    @JvmStatic
    fun getApiService(): ApiService {
        return instance.create(ApiService::class.java)
    }
}
