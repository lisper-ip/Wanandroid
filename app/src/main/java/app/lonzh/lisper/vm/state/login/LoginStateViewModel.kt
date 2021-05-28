package app.lonzh.lisper.vm.state.login

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.lonzh.lisper.R
import app.lonzh.lisper.ext.appContext
import com.blankj.utilcode.util.StringUtils
import com.hjq.toast.ToastUtils

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     5/28/21 4:31 PM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     5/28/21 4:31 PM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class LoginStateViewModel: ViewModel() {
    val account = ObservableField<String>()
    val password = ObservableField<String>()

    fun checkData(): Boolean{
        if(StringUtils.isEmpty(account.get())){
            ToastUtils.show(appContext.getString(R.string.please_input_account))
            return false
        }
        if(StringUtils.isEmpty(password.get())){
            ToastUtils.show(appContext.getString(R.string.please_input_password))
            return false
        }
        return true
    }
}