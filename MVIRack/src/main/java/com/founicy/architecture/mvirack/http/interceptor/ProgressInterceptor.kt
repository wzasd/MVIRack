package com.founicy.architecture.mvirack.http.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class ProgressInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        return originalResponse.newBuilder()
            .body(ProgressResponseBody(originalResponse.body!!))
            .build()
    }
}