package com.founicy.architecture.mvirack.http

data class BaseResponse<T>(
    val code:Int,
    val message:String,
    val result:T
)