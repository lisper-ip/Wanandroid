package app.lonzh.lisper.vm.request.mine

import androidx.lifecycle.MutableLiveData
import app.lonzh.commonlibrary.ext.launchView
import app.lonzh.commonlibrary.vm.BaseViewModel
import app.lonzh.lisper.data.Coin
import app.lonzh.netlibrary.response.PageList
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toLpResponse

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/6/29 6:02 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/6/29 6:02 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class CoinRequestViewModel : BaseViewModel() {
    val coinLiveData by lazy { MutableLiveData<PageList<Coin>>() }

    fun getCoin(index: Int) = launchView({
        val result = RxHttp.get("/lg/coin/list/$index/json").toLpResponse<PageList<Coin>>().await()
        coinLiveData.value = result
    })
}