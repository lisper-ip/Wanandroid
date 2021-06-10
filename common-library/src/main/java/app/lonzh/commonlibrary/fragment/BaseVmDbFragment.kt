package app.lonzh.commonlibrary.fragment

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import app.lonzh.baselibrary.action.TitleBarAction
import app.lonzh.baselibrary.fragment.BaseDBFragment
import app.lonzh.commonlibrary.ext.getVmClazz
import app.lonzh.commonlibrary.vm.BaseViewModel
import com.drake.logcat.LogCat
import com.gyf.immersionbar.ImmersionBar
import com.hjq.bar.TitleBar

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     5/18/21 5:06 PM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     5/18/21 5:06 PM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
abstract class BaseVmDbFragment<VM : BaseViewModel, DB : ViewDataBinding> : BaseDBFragment<DB>(), TitleBarAction {
    lateinit var viewModel: VM

    private var mImmersionBar: ImmersionBar? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firstLoad = true
        viewModel = createViewModel()
        initComponents()
        initView(savedInstanceState)
        createObserver()
    }

    open fun initComponents(){
        titleBar?.setOnTitleBarListener(this)
        initImmersion()
        initObservers()
    }

    private fun initObservers(){
        viewModel.getShowStartEvent().observe(viewLifecycleOwner, {
            LogCat.e("start-request")
        })
        viewModel.getShowLoadingEvent().observe(viewLifecycleOwner, {
            showLoading(it)
            LogCat.e( "loading-request: $it")
        })
        viewModel.getShowErrorEvent().observe(viewLifecycleOwner, {
            showErrorMsg(it)
            LogCat.e( "error-request: $it")
        })
        viewModel.getShowFinishEvent().observe(viewLifecycleOwner, {
            dismissLoading()
            LogCat.e( "finish-request")
        })
        viewModel.getShowLoadingViewEvent().observe(viewLifecycleOwner, {
            LogCat.e("loadingView-request: $it")
        })
        viewModel.getShowEmptyViewEvent().observe(viewLifecycleOwner, {
            LogCat.e("emptyView-request")
        })
        viewModel.getShowErrorViewEvent().observe(viewLifecycleOwner, {
            LogCat.e( "errorView-request")
        })
    }

    abstract fun showLoading(msg: String)

    abstract fun dismissLoading()

    abstract fun showErrorMsg(msg: String)
    /**
     * 初始化沉浸式
     */
    private fun initImmersion() {
        if (isStatusBarEnabled()) {
            statusBarConfig()?.init()
            if (titleBar != null) {
                ImmersionBar.setTitleBar(this, titleBar)
            }
        }
    }

    /**
     * 是否在Fragment使用沉浸式
     */
    open fun isStatusBarEnabled(): Boolean {
        return true
    }

    /**
     * 获取状态栏沉浸的配置对象
     */
    protected fun getStatusBarConfig(): ImmersionBar? {
        return mImmersionBar
    }

    /**
     * 初始化沉浸式
     */
    private fun statusBarConfig(): ImmersionBar? {
        mImmersionBar = ImmersionBar.with(this) // 默认状态栏字体颜色为黑色
            .statusBarDarkFont(isStatusBarDarkFont()) // 解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
        return mImmersionBar
    }

    /**
     * 获取状态栏字体颜色 返回真表示黑色字体
     */
    open fun isStatusBarDarkFont(): Boolean {
        return true
    }

    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getVmClazz(this))
    }

    override val titleBar: TitleBar?
        get() = findTitleBar(view as ViewGroup)

    override fun onLeftClick(v: View) {
        mActivity.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        // 重新初始化状态栏
        statusBarConfig()!!.init()
    }

    /**
     * 初始化view
     */
    abstract fun initView(savedInstanceState: Bundle?)
    /**
     * 创建观察者
     */
    open fun createObserver(){}
}