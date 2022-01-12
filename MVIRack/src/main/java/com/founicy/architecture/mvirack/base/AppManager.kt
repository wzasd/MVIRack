package com.founicy.architecture.mvirack.base

import android.app.Activity
import androidx.fragment.app.Fragment
import java.lang.Exception
import java.util.Stack

/**
 * activity 管理类
 */
object AppManager {
    var activityStack: Stack<Activity> = Stack()
    var fragmentStack: Stack<Fragment> = Stack()

    fun addActivity(activity: Activity){
        activityStack.add(activity)
    }

    fun removeActivity(activity: Activity?){
        activity?.let {
            activityStack.remove(activity)
        }
    }

    fun isActivity(): Boolean{
        return !activityStack.isEmpty()
    }

    fun currentActivity(): Activity {
        return activityStack.lastElement()
    }

    fun finishActivity(){
        var activity = activityStack.lastElement()
        finishActivity(activity)
    }

    fun finishActivity(activity: Activity?){
        activity?.let {
            if (!it.isFinishing){
                it.finish()
            }
        }
    }

    /**
     * 结束指定类名的Activity
     */
    fun finishActivity(cls: Class<*>) {
        for (activity in activityStack) {
            if (activity.javaClass == cls) {
                finishActivity(activity)
                break
            }
        }
    }

    fun finishAllActivity(){
        for (i in activityStack){
            finishActivity(i)
        }
        activityStack.clear()
    }

    fun getActivity(cls: Class<*>):Activity?{
        if(!activityStack.isEmpty()){
            for (activity in activityStack){
                if (activity.javaClass == cls){
                    return activity
                }
            }

        }
        return null
    }

    /**
     * 添加Fragment到堆栈
     */
    fun addFragment(fragment: Fragment?) {
        if (fragmentStack == null) {
            fragmentStack = Stack()
        }
        fragmentStack.add(fragment)
    }

    /**
     * 移除指定的Fragment
     */
    fun removeFragment(fragment: Fragment?) {
        if (fragment != null) {
            fragmentStack.remove(fragment)
        }
    }


    /**
     * 是否有Fragment
     */
    fun isFragment(): Boolean {
        return if (fragmentStack != null) {
            !fragmentStack.isEmpty()
        } else false
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    fun currentFragment(): Fragment? {
        return if (fragmentStack != null) {
            fragmentStack.lastElement()
        } else null
    }


    /**
     * 退出应用程序
     */
    fun AppExit() {
        try {
            finishAllActivity()
            // 杀死该应用进程
//          android.os.Process.killProcess(android.os.Process.myPid());
//            调用 System.exit(n) 实际上等效于调用：
//            Runtime.getRuntime().exit(n)
//            finish()是Activity的类方法，仅仅针对Activity，当调用finish()时，只是将活动推向后台，并没有立即释放内存，活动的资源并没有被清理；当调用System.exit(0)时，退出当前Activity并释放资源（内存），但是该方法不可以结束整个App如有多个Activty或者有其他组件service等不会结束。
//            其实android的机制决定了用户无法完全退出应用，当你的application最长时间没有被用过的时候，android自身会决定将application关闭了。
            //System.exit(0);
        } catch (e: Exception) {
            activityStack.clear()
            e.printStackTrace()
        }
    }

}