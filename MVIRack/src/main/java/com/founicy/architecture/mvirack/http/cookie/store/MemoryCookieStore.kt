package com.founicy.architecture.mvirack.http.cookie.store

import okhttp3.Cookie
import okhttp3.HttpUrl
import java.util.ArrayList
import java.util.HashMap

class MemoryCookieStore:CookieStore {
    private val memoryCookies = HashMap<String, MutableList<Cookie>>()

    override fun saveCookie(url: HttpUrl, cookies: List<Cookie>) {
        val oldCookies: MutableList<Cookie>? = memoryCookies[url.host]
        val needRemove: MutableList<Cookie> = ArrayList()
        for (newCookie in cookies) {
            for (oldCookie in oldCookies!!) {
                if (newCookie.name == oldCookie.name) {
                    needRemove.add(oldCookie)
                }
            }
        }
        oldCookies!!.removeAll(needRemove)
        oldCookies.addAll(cookies)
    }

    override fun saveCookie(url: HttpUrl, cookie: Cookie) {
        val cookies = memoryCookies[url.host]
        val needRemove: MutableList<Cookie> = ArrayList()
        for (item in cookies!!) {
            if (cookie.name == item.name) {
                needRemove.add(item)
            }
        }
        cookies.removeAll(needRemove)
        cookies.add(cookie)
    }

    override fun loadCookie(url: HttpUrl): List<Cookie> {
        var cookies: MutableList<Cookie>? = memoryCookies[url.host]
        if (cookies == null) {
            cookies = ArrayList()
            memoryCookies[url.host] = cookies
        }
        return cookies
    }

    override fun getAllCookie(): List<Cookie> {
        val cookies: MutableList<Cookie> = ArrayList()
        val httpUrls: Set<String> = memoryCookies.keys
        for (url in httpUrls) {
            cookies.addAll(memoryCookies[url]!!)
        }
        return cookies
    }

    override fun getCookie(url: HttpUrl): List<Cookie> {
        val cookies: MutableList<Cookie> = ArrayList()
        val urlCookies: List<Cookie>? = memoryCookies[url.host]
        if (urlCookies != null) cookies.addAll(urlCookies)
        return cookies
    }

    override fun removeCookie(url: HttpUrl, cookie: Cookie): Boolean {
        val cookies = memoryCookies[url.host]
        return cookies!!.remove(cookie)
    }

    override fun removeCookie(url: HttpUrl): Boolean {
        return memoryCookies.remove(url.host) != null
    }

    override fun removeAllCookie(): Boolean {
        memoryCookies.clear()
        return true
    }

}