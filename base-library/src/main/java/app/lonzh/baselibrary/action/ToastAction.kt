package app.lonzh.baselibrary.action

import androidx.annotation.StringRes
import com.hjq.toast.ToastUtils

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     5/18/21 10:56 AM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     5/18/21 10:56 AM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
interface ToastAction {

    fun toast(text: CharSequence){
        ToastUtils.show(text)
    }

    fun toast(@StringRes id: Int){
        ToastUtils.show(id)
    }

    fun toast(obj: Any){
        ToastUtils.show(obj)
    }
}