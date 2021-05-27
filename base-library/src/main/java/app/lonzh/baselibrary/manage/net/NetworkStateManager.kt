package app.lonzh.baselibrary.manage.net

import app.lonzh.baselibrary.livedata.UnPeekLiveData

/**
 *
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2020/9/3 11:45 PM
 * @UpdateUser:     更新者：
 * @UpdateDate:     2020/9/3 11:45 PM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class NetworkStateManager private constructor() {

    val mNetworkStateCallback = UnPeekLiveData<NetState>()

    companion object {
        val instance: NetworkStateManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetworkStateManager()
        }
    }

}