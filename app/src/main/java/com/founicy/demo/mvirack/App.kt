package com.founicy.demo.mvirack

import com.founicy.architecture.mvirack.base.BaseApplication
import com.founicy.architecture.mvirack.utils.FLog
import com.tencent.mmkv.MMKV
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        //是否开启打印日志
        FLog.init(BuildConfig.DEBUG)
        //MMKV 初始化
        MMKV.initialize(this)
    }
}