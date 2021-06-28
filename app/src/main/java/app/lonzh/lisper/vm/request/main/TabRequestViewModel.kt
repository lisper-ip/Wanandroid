package app.lonzh.lisper.vm.request.main

import androidx.lifecycle.MutableLiveData
import app.lonzh.commonlibrary.ext.launch
import app.lonzh.commonlibrary.ext.launchView
import app.lonzh.commonlibrary.vm.BaseViewModel
import app.lonzh.lisper.data.Tab
import com.drake.logcat.LogCat
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toLpResponse

/**
 *
 * @ProjectName:    lisper
 * @Description:    描述
 * @Author:         Lisper
 * @CreateDate:     2021/6/8 7:13 下午
 * @UpdateUser:     Lisper：
 * @UpdateDate:     2021/6/8 7:13 下午
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
class TabRequestViewModel : BaseViewModel() {
    val projectTabLiveData by lazy { MutableLiveData<List<Tab>>() }

    fun getProjectTab() = launch({
        val result = RxHttp.get("/project/tree/json").toLpResponse<List<Tab>>().await()
        LogCat.e(Thread.currentThread().name)
        projectTabLiveData.value = result
    })

    val officialAccountTabLiveData by lazy { MutableLiveData<List<Tab>>() }

    fun getOfficialAccountTab() = launch({
        val result = RxHttp.get("/wxarticle/chapters/json").toLpResponse<List<Tab>>().await()
        officialAccountTabLiveData.value = result
    })
}