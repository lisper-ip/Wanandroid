package app.lonzh.lisper.vm.request.square

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
 * @CreateDate:     2021/6/28 4:35 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/6/28 4:35 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class SystemItemRequestViewModel : BaseViewModel() {
    val articlesLiveData by lazy { MutableLiveData<PageList<ArticleBean>>() }

    val collectArticleLiveData by lazy { MutableLiveData<Any>() }

    val unCollectArticleLiveData by lazy { MutableLiveData<Any>() }

    fun getArticles(index: Int, cid: Int) = launchView({
        val result = RxHttp.get("/article/list/$index/json").add("cid", cid).toLpResponse<PageList<ArticleBean>>().await()
        articlesLiveData.value = result
    })

    fun collectArticle(articleBean: ArticleBean) = launch({
        val result = RxHttp.postJson("/lg/collect/${articleBean.id}/json").toLpResponse<Any>().await()
        collectArticleLiveData.value = result
    })

    fun unCollectArticle(articleBean: ArticleBean) = launch({
        val result = RxHttp.postJson("/lg/uncollect_originId/${articleBean.id}/json").toLpResponse<Any>().await()
        unCollectArticleLiveData.value = result
    })
}