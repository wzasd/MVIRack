package com.founicy.architecture.mvirack.base

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.afollestad.materialdialogs.MaterialDialog
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Created by wzasd on 2021/1/10.
 * 基Activity
 * 这里根据项目业务可以换成你自己熟悉的BaseActivity
 */
abstract class BaseFragment<VM :BaseViewModel<*>> : Fragment(),IBaseView {
    protected var viewModel: VM? = null
    private var dialog: MaterialDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //页面接受的参数方法
        initParam()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel?.let {
            lifecycle.removeObserver(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //初始化页面
        initView()
        processViewModel()
        initViewEvent()
    }

    /**
     * 处理 viewModel 的
     */
    private fun processViewModel() {
        viewModel = initViewModel()
        if (viewModel == null) {
            val modelClass: Class<*>
            val type: Type = javaClass.genericSuperclass
            modelClass = if (type is ParameterizedType){
                type.actualTypeArguments[1] as Class<BaseViewModel<*>>
            }else{
                BaseViewModel::class.java
            }
            viewModel = createViewModel(this, modelClass) as VM
        }
        lifecycle.addObserver(viewModel!!)
    }

    /**
     * 初始化ViewModel
     *
     * @return 继承BaseViewModel的ViewModel
     */
    public fun initViewModel():VM?{
        return null
    }

    private fun initViewEvent(){
        viewModel?._baseViewEvents?.observe(this){
            renderViewEvent(it)
        }
    }

    private fun renderViewEvent(viewEvent:BaseViewEvent) {
        when(viewEvent){
            is BaseViewEvent.ShowDialog ->{
                showDialog(viewEvent.message)
            }
            is BaseViewEvent.DismissDialog->{
                dismissDialog()
            }
            is BaseViewEvent.Finish->{
                activity?.finish();
            }
            is BaseViewEvent.OnBackPressed->{
                activity?.onBackPressed();
            }
            is BaseViewEvent.StartActivity->{
                val clz = viewEvent.params[BaseViewModel.ParameterField.CLASS] as Class<*>
                val bundle = viewEvent.params[BaseViewModel.ParameterField.BUNDLE] as Bundle
                startActivity(clz, bundle)
            }
            is BaseViewEvent.StartContainerActivity->{
                val canonicalName = viewEvent.params[BaseViewModel.ParameterField.CANONICAL_NAME] as String
                val bundle = viewEvent.params[BaseViewModel.ParameterField.BUNDLE] as Bundle
                startContainerActivity(canonicalName, bundle)
            }
            else -> {
                Log.i("BaseFragment","why are you go to here?")
            }
        }
    }

    public fun showDialog(title:String){
        activity?.let {
            dialog = MaterialDialog(requireActivity().baseContext).show {
                title(text = title)
            }
        }
    }

    public fun dismissDialog(){
        dialog?.run {
            this.dismiss()
        }
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public fun startActivity(clz: Class<*>){
        startActivity(Intent(activity, clz))
    }


    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    fun startActivity(clz: Class<*>?, bundle: Bundle?) {
        val intent = Intent(activity, clz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     */
    fun startContainerActivity(canonicalName: String?) {
        startContainerActivity(canonicalName, null)
    }

    /**
     * 跳转容器页面
     *
     * @param canonicalName 规范名 : Fragment.class.getCanonicalName()
     * @param bundle        跳转所携带的信息
     */
    fun startContainerActivity(canonicalName: String?, bundle: Bundle?) {
        val intent = Intent(activity, ContainerActivity::class.java)
        intent.putExtra(ContainerActivity.FRAGMENT, canonicalName)
        if (bundle != null) {
            intent.putExtra(ContainerActivity.BUNDLE, bundle)
        }
        startActivity(intent)
    }


    /**
     * 接口规定方法
     * =====================================================================
     */
    override fun initParam() {

    }

    /**
     * 初始化根布局
     *
     * @return 布局layout的id
     */
    abstract fun initContentView(savedInstanceState: Bundle?): Int


    override fun initView() {

    }

    override fun initData() {

    }

    open fun isBackPressed(): Boolean {
        return false
    }

    /**
     * 创建ViewMod
     *
     * @param cls
     * @param <T>
     * @return
    </T> */
    private inline fun <reified T : ViewModel?> createViewModel(fragment: ViewModelStoreOwner, cls: Class<T>): T {
        return ViewModelProvider(fragment).get(cls)
    }
}