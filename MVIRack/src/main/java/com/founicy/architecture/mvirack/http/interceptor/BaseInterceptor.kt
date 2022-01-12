package com.founicy.architecture.mvirack.http.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class BaseInterceptor(var headers:Map<String,String>?):Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request()
            .newBuilder()
        headers?.let {
            if (it.isNotEmpty()){
                val keys = headers!!.keys
                for (headerKey in keys) {
                    builder.addHeader(headerKey, it[headerKey]!!).build()
                }
            }
        }
        //请求信息
        return chain.proceed(builder.build())
    }
}