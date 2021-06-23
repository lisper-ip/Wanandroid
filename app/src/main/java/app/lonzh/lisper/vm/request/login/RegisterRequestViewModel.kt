package app.lonzh.lisper.vm.request.login

import androidx.lifecycle.MutableLiveData
import app.lonzh.commonlibrary.ext.launch
import app.lonzh.commonlibrary.vm.BaseViewModel
import app.lonzh.lisper.vm.state.login.RegisterStateViewModel
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
class RegisterRequestViewModel : BaseViewModel() {
    val resultLiveData by lazy { MutableLiveData<Any>() }

    fun register(registerStateViewModel: RegisterStateViewModel) {
        if (!registerStateViewModel.checkData()) return
        launch({
            val result = RxHttp.postForm("/user/register")
                .add("username", registerStateViewModel.account.get())
                .add("password", registerStateViewModel.password.get())
                .add("repassword", registerStateViewModel.againPassword.get())
                .toLpResponse<Any>().await()
            resultLiveData.value = result
        }, RequestConfig().loadingMessage("注册中...").setTag("register").isShowLoading(true))
    }
}