package com.founicy.architecture.mvirack.utils

import android.annotation.SuppressLint
import android.content.Context
import java.lang.NullPointerException
import java.lang.UnsupportedOperationException

class Utils {


    constructor(){
        throw UnsupportedOperationException("u can't instantiate me...")
    }

    companion object{
        @SuppressLint("StaticFieldLeak")
        private var mContext: Context? = null
        /**
         * 初始化工具类
         *
         * @param context 上下文
         */
        fun init(context: Context) {
            mContext = context.applicationContext
        }

        /**
         * 获取ApplicationContext
         *
         * @return ApplicationContext
         */
        fun getContext(): Context {
            if (mContext != null) {
                return mContext!!
            }
            throw NullPointerException("should be initialized in application")
        }
    }

}