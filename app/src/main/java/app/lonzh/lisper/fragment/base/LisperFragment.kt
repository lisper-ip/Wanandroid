package app.lonzh.lisper.fragment.base

import androidx.databinding.ViewDataBinding
import androidx.fragment.app.activityViewModels
import app.lonzh.commonlibrary.fragment.BaseVmDbFragment
import app.lonzh.commonlibrary.vm.BaseViewModel
import app.lonzh.lisper.vm.AppDataViewModel
import app.lonzh.lisper.widget.dialog.LoadingDialog
import com.drake.brv.PageRefreshLayout

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/6/6 1:21 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/6/6 1:21 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
abstract class LisperFragment<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmDbFragment<VM, DB>() {
    private val appDataViewModel: AppDataViewModel by activityViewModels()

    override fun showLoading(msg: String){
        LoadingDialog.show(mActivity, msg)
    }

    override fun dismissLoading(){
        LoadingDialog.dismiss(mActivity)
    }

    override fun showErrorMsg(msg: String){
        toast(msg)
    }

    fun isLogin() : Boolean{
        return appDataViewModel.userInfo.value != null
    }
}