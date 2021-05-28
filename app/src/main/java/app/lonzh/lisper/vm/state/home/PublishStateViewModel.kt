package app.lonzh.lisper.vm.state.home

import androidx.databinding.ObservableField
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
 * @CreateDate:     5/27/21 4:58 PM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     5/27/21 4:58 PM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class PublishStateViewModel : ViewModel() {
    val articleContent = ObservableField<String>()
    val articleUrl = ObservableField<String>()

    fun checkData(): Boolean{
        if(StringUtils.isEmpty(articleContent.get())){
            ToastUtils.show(appContext.getString(R.string.please_input_article_content))
            return false
        }
        if(StringUtils.isEmpty(articleUrl.get())){
            ToastUtils.show(appContext.getString(R.string.hint_article_url))
            return false
        }
        return true
    }
}