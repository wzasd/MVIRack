package com.founicy.architecture.mvirack.base

class BaseState()

sealed class BaseViewEvent{
    data class ShowDialog(val message: String) : BaseViewEvent()
    object DismissDialog : BaseViewEvent()
    data class StartActivity(val params: Map<*,*>) : BaseViewEvent()
    data class StartContainerActivity(val params: Map<*,*>) : BaseViewEvent()
    object Finish : BaseViewEvent()
    object OnBackPressed : BaseViewEvent()
}