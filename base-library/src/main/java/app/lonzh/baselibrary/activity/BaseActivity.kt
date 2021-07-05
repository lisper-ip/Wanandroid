package app.lonzh.baselibrary.activity

import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import app.lonzh.baselibrary.action.HandlerAction
import app.lonzh.baselibrary.action.ToastAction
import app.lonzh.baselibrary.manage.net.NetState
import app.lonzh.baselibrary.manage.net.NetworkStateManager
import com.blankj.utilcode.util.KeyboardUtils

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     5/18/21 11:45 AM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     5/18/21 11:45 AM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
abstract class BaseActivity : AppCompatActivity(), HandlerAction, ToastAction {
    private var isUserDb = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isUserDb) {
            setContentView(layoutId)
        } else {
            initDataBind()
        }
        init(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        initComponents()
        initView(savedInstanceState)
        createObserver()
        NetworkStateManager.instance.mNetworkStateCallback.observe(this, {
            onNetworkStateChanged(it)
        })
    }

    /**
     * 网络变化监听 子类重写
     */
    open fun onNetworkStateChanged(netState: NetState) {}

    open fun getContentView(): ViewGroup {
        return findViewById(Window.ID_ANDROID_CONTENT)
    }

    protected fun initSoftKeyboard(){
        getContentView().setOnClickListener{
            hideSoftKeyboard()
        }
    }

    private fun hideSoftKeyboard(){
        currentFocus?.let {
            KeyboardUtils.hideSoftInput(this)
        }
    }

    open fun initComponents(){}

    /**
     * 供子类BaseDbActivity 初始化dataBinding操作
     */
    open fun initDataBind() {}

    abstract val layoutId : Int

    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 创建LiveData数据观察者
     */
    open fun createObserver(){}

    fun userDataBinding(isUserDb: Boolean) {
        this.isUserDb = isUserDb
    }

    override fun onPause() {
        super.onPause()
        hideSoftKeyboard()
    }

    override fun onDestroy() {
        removeCallbacks()
        super.onDestroy()
    }
}