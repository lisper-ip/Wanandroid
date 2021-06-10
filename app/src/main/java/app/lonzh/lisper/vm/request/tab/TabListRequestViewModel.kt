package app.lonzh.lisper.vm.request.tab

import androidx.lifecycle.MutableLiveData
import app.lonzh.commonlibrary.ext.launch
import app.lonzh.commonlibrary.ext.launchList
import app.lonzh.commonlibrary.vm.BaseViewModel
import app.lonzh.lisper.data.ArticleBean
import app.lonzh.netlibrary.response.PageList
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toLpResponse

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/6/10 3:36 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/6/10 3:36 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class TabListRequestViewModel : BaseViewModel() {
    val articleListLiveData by lazy { MutableLiveData<PageList<ArticleBean>>() }

    fun getTabList(tabId: String?, index: Int){
        launchList({
            val result = RxHttp.get("/project/list/$index/json?cid=$tabId").toLpResponse<PageList<ArticleBean>>().await()
            articleListLiveData.value = result
        })
    }
}