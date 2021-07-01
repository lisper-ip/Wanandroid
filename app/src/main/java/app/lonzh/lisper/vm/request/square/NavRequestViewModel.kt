package app.lonzh.lisper.vm.request.square

import androidx.lifecycle.MutableLiveData
import app.lonzh.commonlibrary.ext.launchView
import app.lonzh.commonlibrary.vm.BaseViewModel
import app.lonzh.lisper.data.NavBean
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toLpResponse

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/7/1 4:13 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/7/1 4:13 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class NavRequestViewModel : BaseViewModel() {
    val navLiveData by lazy { MutableLiveData<List<NavBean>>() }

    fun getNav() = launchView({
        val result = RxHttp.get("/navi/json").toLpResponse<List<NavBean>>().await()
        navLiveData.value = result
    })
}