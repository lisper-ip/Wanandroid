package app.lonzh.commonlibrary.activity

import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import app.lonzh.baselibrary.action.TitleBarAction
import app.lonzh.baselibrary.activity.BaseDBActivity
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
 * @CreateDate:     5/18/21 4:56 PM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     5/18/21 4:56 PM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
abstract class BaseVmDbActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseDBActivity<DB>(), TitleBarAction {
    lateinit var viewModel: VM

    private var mImmersionBar: ImmersionBar? = null

    override fun initComponents() {
        viewModel = createViewModel()
        titleBar?.setOnTitleBarListener(this)
        initImmersion()
        initObservers()
    }

    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getVmClazz(this))
    }

    private fun initObservers(){
        viewModel.getShowStartEvent().observe(this, {
            LogCat.e("start-request")
        })
        viewModel.getShowLoadingEvent().observe(this, {
            LogCat.e("loading-request: $it")
        })
        viewModel.getShowErrorEvent().observe(this, {
            LogCat.e("error-request")
        })
        viewModel.getShowFinishEvent().observe(this, {
            LogCat.e("finish-request")
        })
        viewModel.getShowLoadingViewEvent().observe(this, {
            LogCat.e("loadingView-request: $it")
        })
        viewModel.getShowEmptyViewEvent().observe(this, {
            LogCat.e("emptyView-request")
        })
        viewModel.getShowErrorViewEvent().observe(this, {
            LogCat.e( "errorView-request")
        })
    }

    /**
     * 初始化沉浸式
     */
    protected open fun initImmersion() {
        if (isStatusBarEnabled()) {
            val mImmersionBar = createStatusBarConfig()
            if(titleBar != null && mImmersionBar != null){
                mImmersionBar.titleBar(titleBar)
            }
            mImmersionBar?.init()
        }
    }

    /**
     * 初始化沉浸式状态栏
     */
    protected open fun createStatusBarConfig(): ImmersionBar? {
        mImmersionBar = ImmersionBar.with(this) // 默认状态栏字体颜色为黑色
            .statusBarDarkFont(isStatusBarDarkFont())
        return mImmersionBar
    }

    /**
     * 获取状态栏沉浸的配置对象
     */
    open fun getStatusBarConfig(): ImmersionBar? {
        return mImmersionBar
    }

    /**
     * 是否使用沉浸式状态栏
     */
    protected open fun isStatusBarEnabled(): Boolean {
        return true
    }

    /**
     * 状态栏字体深色模式
     */
    protected open fun isStatusBarDarkFont(): Boolean {
        return true
    }

    /**
     * 设置标题栏的标题
     */
    override fun setTitle(@StringRes id: Int) {
        title = getString(id)
    }

    override fun setTitle(title: CharSequence?) {
        super<BaseDBActivity>.setTitle(title)
        titleBar?.title = title
    }

    override val titleBar: TitleBar?
        get() = findTitleBar(getContentView())

    override fun onLeftClick(v: View) {
        onBackPressed()
    }
}