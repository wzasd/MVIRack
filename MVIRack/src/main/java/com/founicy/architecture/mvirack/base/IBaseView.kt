package com.founicy.architecture.mvirack.base

interface IBaseView {
    /**
     * 初始化界面传递参数
     */
    fun initParam()
    /**
     * 初始化界面
     */
    fun initView()
    /**
     * 初始化数据
     */
    fun initData()
}