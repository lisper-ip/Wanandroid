package app.lonzh.lisper.startup

import android.app.Application
import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import app.lonzh.lisper.BR
import app.lonzh.lisper.BuildConfig
import app.lonzh.lisper.R
import app.lonzh.lisper.data.StateData
import app.lonzh.lisper.ext.appContext
import app.lonzh.netlibrary.RxHttpManager
import com.drake.brv.PageRefreshLayout
import com.drake.brv.utils.BRV
import com.drake.logcat.LogCat
import com.drake.statelayout.StateConfig
import com.hjq.bar.TitleBar
import com.hjq.bar.style.TransparentBarStyle
import com.hjq.toast.ToastUtils
import com.rousetime.android_startup.AndroidStartup
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.mmkv.MMKV

/**
 *
 * @ProjectName:    lisper
 * @Description:    主线程初始化
 * @Author:         Lisper
 * @CreateDate:     5/19/21 2:18 PM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     5/19/21 2:18 PM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class CommonMainStartUp : AndroidStartup<String>(){
    /**
     * Return true call the create function on main thread otherwise false.
     */
    override fun callCreateOnMainThread(): Boolean = true

    /**
     * Contains all of the necessary operations to initialize the component.
     * and returns an instance of `T`
     *
     * @param [context]
     */
    override fun create(context: Context): String? {
        MMKV.initialize(context)
        ToastUtils.init(context as Application)
        BRV.modelId = BR.bean
        RxHttpManager.init(context)
        PageRefreshLayout.startIndex = 0
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { mContext, _ ->
            MaterialHeader(mContext)
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { mContext, _ ->
            ClassicsFooter(mContext)
        }
        StateConfig.apply {
            loadingLayout = R.layout.layout_view_loading
            emptyLayout = R.layout.layout_view_state
            errorLayout = R.layout.layout_view_state

            onLoading {
                when(it){
                    is StateData -> {
                        findViewById<TextView>(R.id.tv_msg).text = it.msg
                    }
                    else -> {
                        findViewById<TextView>(R.id.tv_msg).text = appContext.getString(R.string.default_loading)
                    }
                }
            }

            onEmpty {
                when(it){
                    is StateData -> {
                        findViewById<ImageView>(R.id.iv_anim).setImageResource(it.resId)
                        findViewById<TextView>(R.id.tv_msg).text = it.msg
                    }
                    else -> {
                        findViewById<ImageView>(R.id.iv_anim).setImageResource(R.drawable.ic_empty)
                        findViewById<TextView>(R.id.tv_msg).text = appContext.getString(R.string.view_empty_msg)
                    }
                }
            }

            onError {
                when(it){
                    is StateData -> {
                        findViewById<ImageView>(R.id.iv_anim).setImageResource(it.resId)
                        findViewById<TextView>(R.id.tv_msg).text = it.msg
                    }
                    else -> {
                        findViewById<ImageView>(R.id.iv_anim).setImageResource(R.drawable.ic_error)
                        findViewById<TextView>(R.id.tv_msg).text = appContext.getString(R.string.unknown_mistake)
                    }
                }
            }
            setRetryIds(R.id.iv_anim, R.id.tv_msg)
        }
        LogCat.config {
            defaultTag = "lisper"
            enabled = BuildConfig.DEBUG
        }
        return javaClass.simpleName
    }

    /**
     * Return true block the main thread until the startup completed otherwise false.
     *
     * Note: If the function [callCreateOnMainThread] return true, main thread default block.
     */
    override fun waitOnMainThread(): Boolean = true
}