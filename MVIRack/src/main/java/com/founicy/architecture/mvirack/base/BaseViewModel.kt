package com.founicy.architecture.mvirack.base

import android.app.Application
import com.founicy.architecture.mvirack.ext.setEvent
import com.founicy.architecture.mvirack.utils.SingleLiveEvent
import com.founicy.architecture.mvirack.utils.asLiveData
import android.os.Bundle
import androidx.lifecycle.*
import com.founicy.architecture.mvirack.base.BaseViewModel.ParameterField


class BaseViewModel(application: Application):AndroidViewModel(application),IBaseViewModel {
    public var _baseViewEvents:SingleLiveEvent<BaseViewEvent> = SingleLiveEvent()
    public var _baseViewStates:MutableLiveData<BaseState> = MutableLiveData()

    fun showLoadingDialog() {
        showDialog("请稍后...")
    }

    fun showDialog(title: String) {
        _baseViewEvents.setEvent(BaseViewEvent.ShowDialog(title))
    }

    fun dismissDialog(){
        _baseViewEvents.setEvent(BaseViewEvent.DismissDialog)
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    fun startActivity(clz: Class<*>) {
        startActivity(clz, null)
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    fun startActivity(clz: Class<*>, bundle: Bundle?) {
        val params: MutableMap<String, Any> = HashMap()
        params[ParameterField.CLASS] = clz
        if (bundle != null) {
            params[ParameterField.BUNDLE] = bundle
        }
        _baseViewEvents.setEvent(BaseViewEvent.StartActivity(params))
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     */
    fun startContainerActivity(canonicalName: String) {
        startContainerActivity(canonicalName, null)
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     * @param bundle        跳转所携带的信息
     */
    fun startContainerActivity(canonicalName: String, bundle: Bundle?) {
        val params: MutableMap<String, Any> = HashMap()
        params[ParameterField.CANONICAL_NAME] = canonicalName
        if (bundle != null) {
            params[ParameterField.BUNDLE] = bundle
        }
        _baseViewEvents.setEvent(BaseViewEvent.StartContainerActivity(params))
    }

    /**
     * 关闭界面
     */
    public fun finish(){
        _baseViewEvents.setEvent(BaseViewEvent.Finish)
    }

    public fun onBackPressed(){
        _baseViewEvents.setEvent(BaseViewEvent.OnBackPressed)
    }

    object ParameterField {
        var CLASS = "CLASS"
        var CANONICAL_NAME = "CANONICAL_NAME"
        var BUNDLE = "BUNDLE"
    }
}