package app.lonzh.lisper.vm.request.home

import androidx.lifecycle.MutableLiveData
import app.lonzh.commonlibrary.ext.launch
import app.lonzh.commonlibrary.vm.BaseViewModel
import app.lonzh.lisper.vm.state.home.PublishStateViewModel
import app.lonzh.netlibrary.config.RequestConfig
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toLpResponse

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     5/27/21 5:28 PM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     5/27/21 5:28 PM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class PublishRequestViewModel: BaseViewModel() {
    val resultLiveData by lazy { MutableLiveData<String>() }

    fun shareArticle(publishStateViewModel: PublishStateViewModel){
        if(!publishStateViewModel.checkData()) return
        launch({
            val result = RxHttp.postJson("/lg/user_article/add/json")
                .add("title", publishStateViewModel.articleContent.get())
                .add("link", publishStateViewModel.articleUrl.get())
                .toLpResponse<String>()
                .await()
            resultLiveData.value = result
        }, RequestConfig().isShowLoading(true).setTag("shareArticle").loadingMessage("发布中..."))
    }
}