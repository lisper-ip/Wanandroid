package app.lonzh.lisper.vm.request.main

import androidx.lifecycle.MutableLiveData
import app.lonzh.commonlibrary.ext.launch
import app.lonzh.commonlibrary.vm.BaseViewModel
import app.lonzh.lisper.data.UserInfo
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toLpResponse

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/6/25 5:10 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/6/25 5:10 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class MineRequestViewModel : BaseViewModel() {
    val userInfoLiveData by lazy { MutableLiveData<UserInfo>() }

    fun getUserInfo() = launch({
        val result = RxHttp.get("/lg/coin/userinfo/json").toLpResponse<UserInfo>().await()
        userInfoLiveData.value = result
    })
}