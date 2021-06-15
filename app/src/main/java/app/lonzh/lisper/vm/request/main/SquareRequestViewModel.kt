package app.lonzh.lisper.vm.request.main

import androidx.lifecycle.MutableLiveData
import app.lonzh.commonlibrary.ext.launchView
import app.lonzh.commonlibrary.vm.BaseViewModel
import app.lonzh.lisper.data.Tab
import app.lonzh.netlibrary.config.RequestConfig
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toLpResponse

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/6/15 1:56 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/6/15 1:56 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class SquareRequestViewModel : BaseViewModel() {
    val squareLiveData by lazy { MutableLiveData<List<Tab>>() }

    fun getSquareList(){
        launchView({
            val result = RxHttp.get("/tree/json").toLpResponse<List<Tab>>().await()
            squareLiveData.value = result
        }, RequestConfig().setTag("tree"))
    }
}