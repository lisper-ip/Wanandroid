package app.lonzh.baselibrary.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.lonzh.baselibrary.action.HandlerAction
import app.lonzh.baselibrary.action.ToastAction
import app.lonzh.baselibrary.activity.BaseActivity
import app.lonzh.baselibrary.manage.net.NetState
import app.lonzh.baselibrary.manage.net.NetworkStateManager

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     5/18/21 4:13 PM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     5/18/21 4:13 PM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
abstract class BaseFragment : Fragment(), HandlerAction, ToastAction {
    var firstLoad: Boolean = true

    lateinit var mActivity: BaseActivity

    override fun onResume() {
        super.onResume()
        if(firstLoad){
            postDelayed({
                lazyLoad()
                //在Fragment中，只有懒加载过了才能开启网络变化监听
                NetworkStateManager.instance.mNetworkStateCallback.observe(
                    viewLifecycleOwner,
                    {
                        //不是首次订阅时调用方法，防止数据第一次监听错误
                        if (!firstLoad) {
                            onNetworkStateChanged(it)
                        }
                    })
                firstLoad = !firstLoad
            }, 300)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId(), container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as BaseActivity
    }

    /**
     * 网络变化监听 子类重写
     */
    open fun onNetworkStateChanged(netState: NetState) {}

    /**
     * 当前Fragment绑定的视图布局
     */
    abstract fun layoutId(): Int

    open fun lazyLoad(){

    }

    override fun onDetach() {
        removeCallbacks()
        super.onDetach()
    }
}