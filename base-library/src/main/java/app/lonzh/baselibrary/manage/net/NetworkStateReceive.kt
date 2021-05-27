package app.lonzh.baselibrary.manage.net

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

/**
 *
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2020/9/3 11:35 PM
 * @UpdateUser:     更新者：
 * @UpdateDate:     2020/9/3 11:35 PM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class NetworkStateReceive : BroadcastReceiver() {
    private var isInit = true
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ConnectivityManager.CONNECTIVITY_ACTION) {
            if(!isInit){
                if (!NetHelper.isNetWorkAvailable(context)) {
                    //收到没有网络时判断之前的值是不是有网络，如果有网络才提示通知 ，防止重复通知
                    NetworkStateManager.instance.mNetworkStateCallback.value?.let {
                        if(it.isSuccess){
                            //没网
                            NetworkStateManager.instance.mNetworkStateCallback.postValue(NetState(isSuccess = false, networkType = NetworkType.NETWORK_NO))
                        }
                        return
                    }
                    NetworkStateManager.instance.mNetworkStateCallback.postValue(NetState(isSuccess = false, networkType = NetworkType.NETWORK_NO))
                }else{
                    //收到有网络时判断之前的值是不是没有网络，如果没有网络才提示通知 ，防止重复通知
                    NetworkStateManager.instance.mNetworkStateCallback.value?.let {
                        if(!it.isSuccess){
                            //有网络了
                            NetworkStateManager.instance.mNetworkStateCallback.postValue(NetState(isSuccess = true, networkType = NetHelper.getNetWorkType(context)))
                        }
                        return
                    }
                    NetworkStateManager.instance.mNetworkStateCallback.postValue(NetState(isSuccess = true, networkType = NetHelper.getNetWorkType(context)))
                }
            }
            isInit = false
        }
    }
}