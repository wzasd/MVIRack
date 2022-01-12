package com.founicy.architecture.mvirack.http.cookie

import com.founicy.architecture.mvirack.http.cookie.store.CookieStore
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import java.lang.IllegalArgumentException


class CoolieJarImpl(var cookieStore: CookieStore?):CookieJar {
    init {
        if (cookieStore == null){
            throw IllegalArgumentException("cookieStore can not be null!")
        }
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return cookieStore!!.loadCookie(url)
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cookieStore!!.saveCookie(url, cookies)
    }
}