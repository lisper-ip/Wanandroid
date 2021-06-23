package app.lonzh.lisper.vm.request.mine

import androidx.lifecycle.MutableLiveData
import app.lonzh.commonlibrary.ext.launch
import app.lonzh.commonlibrary.vm.BaseViewModel
import app.lonzh.netlibrary.config.RequestConfig
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toLpResponse

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/6/23 3:40 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/6/23 3:40 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class SettingRequestViewModel : BaseViewModel() {
    val resultLiveData by lazy { MutableLiveData<Any>() }

    fun logout() = launch({
        val result = RxHttp.get("/user/logout/json").toLpResponse<Any>().await()
        resultLiveData.value = result
    }, RequestConfig().isShowLoading(true).setTag("logout").loadingMessage("正在退出..."))
}