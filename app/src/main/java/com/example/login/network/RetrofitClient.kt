package com.example.login.network

import com.example.login.api.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.CookieJar
import okhttp3.Cookie
import okhttp3.HttpUrl
import java.net.CookieHandler
import java.net.CookieManager
import java.net.CookiePolicy
import java.net.CookieStore

object RetrofitClient {
    private var retrofit: Retrofit? = null
    private const val BASE_URL = "http://10.0.2.2:8080/" // 실제 서버 URL

    private val cookieJar = object : CookieJar {
        private val cookies = mutableListOf<Cookie>()

        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            this.cookies.addAll(cookies)
        }

        override fun loadForRequest(url: HttpUrl): List<Cookie> {
            return cookies.filter { it.matches(url) }
        }
    }



    private val instance: Retrofit
        get() {
            if (retrofit == null) {

                val client = OkHttpClient.Builder()
                    .cookieJar(cookieJar)
                    .build()

                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
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
