package app.lonzh.lisper.vm.request.login

import androidx.lifecycle.MutableLiveData
import app.lonzh.commonlibrary.ext.launch
import app.lonzh.commonlibrary.vm.BaseViewModel
import app.lonzh.lisper.data.UserInfo
import app.lonzh.lisper.vm.state.login.LoginStateViewModel
import app.lonzh.netlibrary.config.RequestConfig
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toLpResponse

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     5/28/21 5:20 PM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     5/28/21 5:20 PM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class LoginRequestViewModel : BaseViewModel() {
    val resultLiveData by lazy { MutableLiveData<UserInfo>() }

    fun login(loginStateViewModel: LoginStateViewModel){
        if(!loginStateViewModel.checkData()) return
        launch({
            val result = RxHttp.postForm("/user/login")
                .add("username", loginStateViewModel.account.get())
                .add("password", loginStateViewModel.password.get())
                .toLpResponse<UserInfo>().await()
            resultLiveData.value = result
        }, RequestConfig().loadingMessage("登录中...").setTag("login").isShowLoading(true))
    }
}