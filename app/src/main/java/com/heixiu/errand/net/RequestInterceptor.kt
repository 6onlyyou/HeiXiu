package com.wwzz.androidbase.net

import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by YuanGang on 2018/3/19.
 */

class RequestInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val builder = chain.request().newBuilder()

        val request = builder.build()
        val t1 = System.nanoTime()
        Log.i("RequestInterceptor", String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()))
        val response = chain.proceed(request)
        if (response.isSuccessful) {
            val requestUrl = response.request().method() + " " + response.request().url().toString()
            val responseCode = response.code()
            val message = responseCode.toString() + " on " + requestUrl
            Log.i("RequestInterceptor", message)
        }

        val t2 = System.nanoTime()
        Log.i("RequestInterceptor", String.format("Received response for %s in %.1fms%n%s", response.request().url(), java.lang.Double.valueOf((t2 - t1).toDouble() / 1000000.0), response.headers()))
        return response
    }

}
