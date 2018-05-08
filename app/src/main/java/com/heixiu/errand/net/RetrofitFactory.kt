package com.heixiu.errand.net

import com.heixiu.errand.net.AndroidBase
import com.heixiu.errand.net.ApiService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 * Created by YuanGang on 2018/3/19.
 */
object RetrofitFactory {
    private lateinit var retrofit: Retrofit
    private var isHttps: Boolean = true

    fun init() {
        retrofit = Retrofit.Builder()
                .baseUrl(if (isHttps) AndroidBase.BASE_HTTPS_URL else AndroidBase.BASE_HTTP_URL)
                .client(OkHttpFactory.getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    fun getRetrofit(): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}