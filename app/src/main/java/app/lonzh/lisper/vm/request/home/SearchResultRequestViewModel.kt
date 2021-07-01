package app.lonzh.lisper.vm.request.home

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
 * @CreateDate:     2021/6/29 11:45 上午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/6/29 11:45 上午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class SearchResultRequestViewModel : BaseViewModel() {

    val searchLiveData by lazy { MutableLiveData<PageList<ArticleBean>>() }

    val collectArticleLiveData by lazy { MutableLiveData<Any>() }

    val unCollectArticleLiveData by lazy { MutableLiveData<Any>() }

    fun search(index: Int, keyword: String) = launchView({
        val result = RxHttp.postForm("/article/query/$index/json").add("k", keyword).toLpResponse<PageList<ArticleBean>>().await()
        searchLiveData.value = result
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