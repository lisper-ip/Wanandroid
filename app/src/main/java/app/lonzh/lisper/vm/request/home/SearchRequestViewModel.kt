package app.lonzh.lisper.vm.request.home

import androidx.lifecycle.MutableLiveData
import app.lonzh.commonlibrary.ext.launch
import app.lonzh.commonlibrary.vm.BaseViewModel
import app.lonzh.lisper.data.HotKey
import app.lonzh.lisper.data.HotWeb
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toLpResponse

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/7/1 6:59 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/7/1 6:59 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class SearchRequestViewModel : BaseViewModel() {
    val hotLiveData by lazy { MutableLiveData<List<HotKey>>() }

    fun getHotKey() = launch({
        val result = RxHttp.get("/hotkey/json").toLpResponse<List<HotKey>>().await()
        hotLiveData.value = result
    })

    val hotWebLiveData by lazy { MutableLiveData<List<HotWeb>>() }

    fun getHotWeb() = launch({
        val result = RxHttp.get("/friend/json").toLpResponse<List<HotWeb>>().await()
        hotWebLiveData.value = result
    })

}