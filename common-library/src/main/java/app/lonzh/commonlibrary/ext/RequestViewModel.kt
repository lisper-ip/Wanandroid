package app.lonzh.commonlibrary.ext

import androidx.lifecycle.rxLifeScope
import app.lonzh.commonlibrary.BuildConfig
import app.lonzh.commonlibrary.R
import app.lonzh.commonlibrary.vm.BaseViewModel
import app.lonzh.netlibrary.config.RequestConfig
import kotlinx.coroutines.CoroutineScope

/**
 *
 * @ProjectName:    lisper
 * @Description:    普通请求
 * @Author:         Lisper
 * @CreateDate:     5/18/21 5:36 PM
 * @UpdateUser:     Lisper：
 * @UpdateDate:     5/18/21 5:36 PM
 * @UpdateRemark:   更新说明：
 * @Version:        1.0
 */
fun BaseViewModel.launch(block: suspend CoroutineScope.() -> Unit,
           requestConfig: RequestConfig? = null){
    if(!checkRequestConfig(requestConfig)) return
    rxLifeScope.launch(block, {
        it.errorMsg?.let { errorMsg ->
            if(BuildConfig.DEBUG){
                getShowErrorEvent().setValue("$errorMsg,错误代号:${it.errorCode}")
            } else {
                getShowErrorEvent().setValue(errorMsg)
            }
        }
    }, {
        getShowStartEvent().call()
        requestConfig?.let {
            if(it.showLoading){
                getShowLoadingEvent().setValue(it.loadingMessage)
            }
            if(it.tag.isNotEmpty()){
                tagList.add(it.tag)
            }
        }
    }, {
        getShowFinishEvent().setValue(requestConfig?.tag ?: "")
        requestConfig?.let {
            if(it.tag.isNotEmpty()){
                tagList.remove(it.tag)
            }
        }
    })
}

/**
 * 列表请求
 */
fun BaseViewModel.launchView(block: suspend CoroutineScope.() -> Unit,
                         requestConfig: RequestConfig? = null){
    if(!checkRequestConfig(requestConfig)) return
    rxLifeScope.launch(block, {
        it.errorMsg?.let { errorMsg ->
            if(BuildConfig.DEBUG){
                getShowErrorViewEvent().setValue("$errorMsg,错误代号:${it.errorCode}")
            } else {
                getShowErrorViewEvent().setValue(errorMsg)
            }
        }
    }, {
        getShowStartEvent().call()
        requestConfig?.let {
            if(it.tag.isNotEmpty()){
                tagList.add(it.tag)
            }
        }
    }, {
        getShowFinishEvent().setValue(requestConfig?.tag ?: "")
        requestConfig?.let {
            if(it.tag.isNotEmpty()){
                tagList.remove(it.tag)
            }
        }
    })
}