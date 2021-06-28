package app.lonzh.lisper.vm.request.mine

import androidx.lifecycle.MutableLiveData
import app.lonzh.commonlibrary.ext.launch
import app.lonzh.commonlibrary.ext.launchView
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
 * @CreateDate:     2021/6/25 2:27 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/6/25 2:27 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class CollectRequestViewModel : BaseViewModel() {
    val resultLiveData by lazy { MutableLiveData<PageList<ArticleBean>>() }

    fun getCollect(index: Int) = launchView({
        val result = RxHttp.get("/lg/collect/list/$index/json ").toLpResponse<PageList<ArticleBean>>().await()
        result.datas.map {
            it.collect = true
        }
        resultLiveData.value = result
    })

    val unCollectArticleLiveData by lazy { MutableLiveData<Any>() }

    fun unCollectArticle(articleBean: ArticleBean) = launch({
        val result = RxHttp.postJson("/lg/uncollect_originId/${articleBean.originId}/json").toLpResponse<Any>().await()
        unCollectArticleLiveData.value = result
    })
}