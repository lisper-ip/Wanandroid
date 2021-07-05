package app.lonzh.lisper.fragment.base

import androidx.databinding.ViewDataBinding
import androidx.fragment.app.activityViewModels
import app.lonzh.baselibrary.dialog.BaseDialog
import app.lonzh.baselibrary.util.Constant
import app.lonzh.commonlibrary.fragment.BaseVmDbFragment
import app.lonzh.commonlibrary.vm.BaseViewModel
import app.lonzh.lisper.vm.AppDataViewModel
import app.lonzh.lisper.widget.dialog.LoadingDialog
import com.blankj.utilcode.util.KeyboardUtils

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
    private var loadingDialog: BaseDialog? = null

    override fun showLoading(msg: String){
        activity?.let {
            if (!it.isFinishing) {
                if (loadingDialog == null) {
                    loadingDialog = LoadingDialog.Builder(it)
                        .setMessage(msg)
                        .create()
                }
                loadingDialog?.show()
            }
        }
    }

    override fun dismissLoading(){
        loadingDialog?.dismiss()
        loadingDialog = null
    }

    private fun hideSoftInput() {
        postDelayed({
            activity?.let { KeyboardUtils.hideSoftInput(it) }
        }, Constant.RELAY_KEYBOARD)
    }

    fun hideSoftInputAfter(block: () -> Unit){
        postDelayed({
            activity?.let { KeyboardUtils.hideSoftInput(it) }
            block.invoke()
        }, Constant.RELAY_KEYBOARD)
    }

    override fun onDestroyView() {
        hideSoftInput()
        super.onDestroyView()
    }

    override fun showErrorMsg(msg: String){
        toast(msg)
    }

    fun isLogin() : Boolean{
        return appDataViewModel.userInfo.value != null
    }
}