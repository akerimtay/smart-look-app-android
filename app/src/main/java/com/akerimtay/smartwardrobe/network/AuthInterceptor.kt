package com.akerimtay.smartwardrobe.network

import com.akerimtay.smartwardrobe.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val builder = request.url.newBuilder()
        val url = builder.addQueryParameter("appid", BuildConfig.API_KEY).build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}