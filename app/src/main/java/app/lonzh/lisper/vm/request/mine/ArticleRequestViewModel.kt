package app.lonzh.lisper.vm.request.mine

import androidx.lifecycle.MutableLiveData
import app.lonzh.commonlibrary.ext.launch
import app.lonzh.commonlibrary.ext.launchView
import app.lonzh.commonlibrary.vm.BaseViewModel
import app.lonzh.lisper.data.ArticleEntity
import app.lonzh.netlibrary.config.RequestConfig
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toLpResponse

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/6/30 10:45 上午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/6/30 10:45 上午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class ArticleRequestViewModel : BaseViewModel() {
    val articlesLiveData by lazy { MutableLiveData<ArticleEntity>() }

    fun getArticles(index: Int) = launchView({
        val result = RxHttp.get("/user/lg/private_articles/$index/json").toLpResponse<ArticleEntity>().await()
        articlesLiveData.value = result
    })

    val deleteLiveData by lazy { MutableLiveData<Any>() }

    fun delete(id: Int) = launch({
        val result = RxHttp.postJson("/lg/user_article/delete/$id/json").toLpResponse<Any>().await()
        deleteLiveData.value = result
    }, RequestConfig().loadingMessage("正在删除中...").setTag("delete-article").isShowLoading(true))
}